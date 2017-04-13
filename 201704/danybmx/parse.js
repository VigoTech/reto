const indentRules = {
  char: '·',
  length: 4,
};

module.exports = (src) => {
  let indentLevel = 0;
  let currentLine = 0;
  let errors = [];

  const blocks = [
    { open: 'IF', close: 'ENDIF', opened: 0, lastOpenLine: null },
    { open: 'FOR', close: 'NEXT', opened: 0, lastOpenLine: null },
  ];

  const closeBlock = (b) => {
    indentLevel--;
    b.opened--;
    if (b.opened < 0) errors.push(`Line ${currentLine}: Closing unopened ${b.open}`);
  }

  const openBlock = (b) => {
    indentLevel++;
    b.opened++;
    b.lastOpenLine = currentLine;
  }

  const indent = () => Array((indentLevel > 0 ? indentLevel : 0) * indentRules.length + 1).join(indentRules.char)

  let lines = src
    .split("\n").splice(2)
    .map((i) => i.replace(new RegExp(/^(·|»)*/), ''))
    .map((i) => {
      currentLine++;
      blocks.map((b) => i.match(new RegExp('^' + b.close)) && closeBlock(b));
      const line = indent() + i;
      blocks.map((b) => i.match(new RegExp('^' + b.open)) && openBlock(b));
      return line;
    });

  blocks.map((b) => {
    if (b.opened > 0) { errors.push(`Unclosed ${b.open} opened on line ${b.lastOpenLine}`) }
  });

  if (errors.length > 0) {
    throw new Error(errors.join(", "));
  }

  return lines.join("\n");
}
