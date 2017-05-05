module DinoSetoSpace
  def self.autoindent(input)
    lines = input.lines
    current_indent, indent_width, char = [0, lines[1].length-1, lines[1][0]]

    lines.drop(2).map {|line|
      line = line.delete('·»')
      current_indent -= indent_width if line.start_with?('ENDIF', 'NEXT')
      new_line = char * current_indent + line
      current_indent += indent_width if line.start_with?('FOR', 'IF')
      new_line
    }.join
  end
end
