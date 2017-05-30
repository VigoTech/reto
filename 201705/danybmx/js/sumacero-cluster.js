const cluster = require('cluster');
const numCPUs = require('os').cpus().length;
const v8 = require('v8');
v8.setFlagsFromString('--max_old_space_size=2048');

if (cluster.isMaster) {
  let running = 0;
  let found = false;
  for (let i = 0; i < numCPUs; i++) {
    const worker = cluster.fork();
    running++;
  }
  cluster.on('message', () => {
    console.log("FOUND");
    found = true;
    for (let id in cluster.workers) {
      cluster.workers[id].kill();
    }
  });
  cluster.on('exit', (worker, code, signal) => {
    running--;
    if (running === 0 && !found) {
      console.log("NOT FOUND");
      process.exit(-1);
    }
  });
} else {
  let numbers = process.argv.splice(2).map((i) => parseInt(i));
  let nlength = numbers.length;
  const padding = new Array(nlength).join("0");
  const calculate = (n, x) => {
    const operation = (padding + (x).toString(2)).substr(-n.length);
    let sum = [];
    for (let y = 0; y <= n.length; y++) {
      if (operation[y] == 1) sum.push(n[y]);
    }
    return sum.reduce((t,s) => t+=s);
  }
  const lastNumbers = [];
  if (nlength > 20) {
    const n = Math.floor(nlength / 4);
    const lNumbers = numbers.slice(n);
    for (let z = 1; z < Math.pow(2, lNumbers.length); z++) {
      lastNumbers.push(calculate(lNumbers, z));
    }
    numbers = numbers.slice(0, n);
    nlength = numbers.length;
  }
  const operations = Math.pow(2, nlength);
  const eachWorker = Math.ceil(operations / numCPUs);
  const wid = cluster.worker.id;
  const from = (eachWorker*(wid-1))+1;
  let to = eachWorker*wid;
  if (to > operations) to = operations;
  for (let x = from; x < to; x++) {
    tot = calculate(numbers, x);
    if (tot === 0 || lastNumbers.indexOf(-tot) > -1) {
      cluster.worker.send(1);
    }
  }
  cluster.worker.kill(0);
}
