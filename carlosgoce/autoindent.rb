current_indent = 0
open_op = ['FOR', 'IF']
close_op = ['ENDIF', 'NEXT']

input = File.readlines('input')
indent = input[1].length
indent_with = input[1][0]

lines = input.drop(2).each_with_index.map {|line, i|
  line = line.delete('·»')
  current_indent += indent if line.start_with?('FOR', 'IF')
  current_indent -= indent if line.start_with?('ENDIF', 'NEXT')
  indent_with * current_indent + line
}

puts lines
