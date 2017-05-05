const indentRules = {
  char: '·',
  length: 4,
};

module.exports = (src) => {
  let indentLevel = 0;
  let currentLine = 0;
  let errors = [];

  const blocks = [
    { open: 'IF', close: 'ENDIF', instances: [] },
    { open: 'FOR', close: 'NEXT', instances: [] },
  ];

  const previousOpenedBlock = () => {
    let lastOpenedBlock = null;
    blocks.map((b) => b.instances.map((line) => {
      if (line > lastOpenedBlock || lastOpenedBlock === null) lastOpenedBlock = b.open
    }));
    return lastOpenedBlock;
  }

  const closeBlock = (b) => {
    indentLevel--;
    if (b.instances.length < 1) {
      errors.push(`Line ${currentLine}: Closing unopened ${b.open}`);
    } else if (previousOpenedBlock() !== b.open) {
      errors.push(`Line ${currentLine}: Closing ${b.open} but ${previousOpenedBlock()} still open`)
    }
    b.instances.pop();
  }

  const openBlock = (b) => {
    indentLevel++;
    b.instances.push(currentLine);
  }

  const indent = () => Array((indentLevel > 0 ? indentLevel : 0) * indentRules.length + 1).join(indentRules.char)

  let lines = src.split("\n").splice(2)
    .map((i) => i.replace(new RegExp(/^(·|»)*/), ''))
    .map((i) => {
      currentLine++;
      blocks.map((b) => i.match(new RegExp('^' + b.close)) && closeBlock(b));
      const line = indent() + i;
      blocks.map((b) => i.match(new RegExp('^' + b.open)) && openBlock(b));
      return line;
    });

  blocks.map((b) => {
    b.instances.map((i) => {
      if (b.instances.length > 0) { errors.push(`Unclosed ${b.open} opened on line ${i}`) }
    })
  });

  if (errors.length > 0) {
    throw new Error(errors.join(", "));
  }

  return lines.join("\n");
}
