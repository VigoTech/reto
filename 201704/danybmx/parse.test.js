const fs = require('fs');
const parse = require('./parse');

test('Correct code should be indented correctly', () => {
  const res = parse(fs.readFileSync('test/resources/ok.sample', 'UTF-8'));
  expect(res).toBe(fs.readFileSync('test/resources/ok.result', 'UTF-8'));
});

test('Code with unclosed IF should throw an error', () => {
  expect(() => {
    parse(fs.readFileSync('test/resources/ko_1.sample', 'UTF-8'))
  }).toThrowError('Line 4: Closing FOR but IF still open, Unclosed IF opened on line 2');
});

test('Code with unclosed IF and FOR should throw an error', () => {
  expect(() => {
    parse(fs.readFileSync('test/resources/ko_2.sample', 'UTF-8'))
  }).toThrowError('Unclosed IF opened on line 2, Unclosed FOR opened on line 1');
});

test('Code with unopened IF and unclosed FOR should throw an error', () => {
  expect(() => {
    parse(fs.readFileSync('test/resources/ko_3.sample', 'UTF-8'))
  }).toThrowError('Line 3: Closing unopened IF, Unclosed FOR opened on line 1');
});

test('Code with unopened IF should throw an error', () => {
  expect(() => {
    parse(fs.readFileSync('test/resources/ko_4.sample', 'UTF-8'))
  }).toThrowError('Line 4: Closing unopened IF');
});
