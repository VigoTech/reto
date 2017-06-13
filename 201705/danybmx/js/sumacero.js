module.exports = (numbers) => {
  const nlength = numbers.length;
  const operations = Math.pow(2, nlength);
  let padding = new Array(nlength).join("0");
  for (let x = 0; x < operations; x++) {
    const operation = (padding + (x >>> 0).toString(2)).substr(-nlength);
    let sum = [];
    for (let y = 0; y < numbers.length; y++) {
      if (operation[y] == 1) sum.push(numbers[y]);
    }
    if (sum.length > 0 && sum.reduce((t,s) => t+=s) === 0) return true;
  }
  return false;
}
