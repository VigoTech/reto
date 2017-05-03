from io import StringIO
import os.path

from reto import parse


CWD = os.path.dirname(__file__)


def test_1():
    in_file = open(os.path.join(CWD, '1.in'), 'r', encoding='utf-8')
    out_file = StringIO()
    parse(in_file, out_file)
    out_file.seek(0)

    solution = open(os.path.join(CWD, '1.out'), 'r', encoding='utf-8')

    assert out_file.read() == solution.read()
