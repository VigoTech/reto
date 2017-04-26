module DinoSetoSpace
  def self.autoindent(input)
    current_indent = 0
    indent = input[1].length - 1
    indent_with = input[1][0]

    input.drop(2).map {|line|
      line = line.delete('·»')
      current_indent -= indent if line.start_with?('ENDIF', 'NEXT')
      new_line = indent_with * current_indent + line
      current_indent += indent if line.start_with?('FOR', 'IF')
      new_line
    }.join
  end
end
