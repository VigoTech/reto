def tokenizer(s):
    """
    Split a code line in tokens.
    """
    tokens = s.lstrip('·»').rstrip('\n').split(' ')
    for n, token in enumerate(tokens):
        if n > 0:
            yield ' '
        yield token
    yield '\n'


def indentation(level, word):
    """
    Returns a string formed by word repeated n times.
    """
    return ''.join([level * word])


class Parser:
    def __init__(self, indent_word):
        self.indent_word = indent_word
        self.tokens = []
        self.stack = []
        self.new_line = False

    def add(self, token):
        if token == '\n':
            self.tokens.append('\n')
            self.new_line = True
            return

        if token in ['NEXT', 'ENDIF']:
            block = self.stack.pop(-1)

        if self.new_line:
            self.tokens.append(indentation(len(self.stack), self.indent_word))
            self.new_line = False

        if token in ['FOR', 'IF']:
            self.stack.append(token)

        self.tokens.append(token)

    def __str__(self):
        return ''.join(self.tokens)


def parse(in_file, out_file):
    number_of_lines = next(in_file)  # noqa: W0612: unused variable
    indent_word = next(in_file).strip()
    parser = Parser(indent_word)
    for line in in_file:
        tokens = tokenizer(line)
        for token in tokens:
            parser.add(token)
    out_file.write(str(parser))
