module DinoSetoSpace
  def self.autoindent(input)
    current_indent = 0
    open_op = ['FOR', 'IF']
    close_op = ['ENDIF', 'NEXT']

    indent = input[1].length - 1
    indent_with = input[1][0]

    lines = input.drop(2).each_with_index.map {|line, i|
      line = line.delete('·»')
      current_indent -= indent if line.start_with?('ENDIF', 'NEXT')
      new_line = indent_with * current_indent + line
      current_indent += indent if line.start_with?('FOR', 'IF')
      new_line
    }

    lines.join
  end
end
