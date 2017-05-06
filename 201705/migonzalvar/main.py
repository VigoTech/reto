from itertools import combinations

import pytest


def two_sum_zero(numbers):
    if 0 in numbers:
        return True
    for case in combinations(numbers, 2):
        if sum(case) == 0:
            return True
    return False


@pytest.mark.parametrize('source, result', [
    ([1, 2, 3], False),
    ([-5, -3, -1, 2, 4, 6], False),
    ([], False),
    ([-1, 1], True),
    ([-97364, -71561, -69336, 19675, 71561, 97863], True),
    ([-53974, -39140, -36561, -23935, -15680, 0], True),
    ([-2, 2, 3], True),
])
def test_acceptance_first(source, result):
    assert two_sum_zero(source) is result
