import pytest

from reto import check_opposite_number_in


@pytest.mark.parametrize('list, result', [
    ([1, 2, 3], False),
    ([-5, -3, -1, 2, 4, 6], False),
    ([], False),
    ([-1, 1], True),
    ([-97364, -71561, -69336, 19675, 71561, 97863], True),
    ([-53974, -39140, -36561, -23935, -15680, 0], True),
    ([-2, 2, 3], True),
])
def test_examples(list, result):
    assert check_opposite_number_in(list) == result