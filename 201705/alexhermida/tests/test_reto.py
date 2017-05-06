import pytest

from reto import check_opposite_number_in, check_adding_elements


@pytest.mark.parametrize('int_list, result', [
    ([1, 2, 3], False),
    ([-5, -3, -1, 2, 4, 6], False),
    ([], False),
    ([-1, 1], True),
    ([-97364, -71561, -69336, 19675, 71561, 97863], True),
    ([-53974, -39140, -36561, -23935, -15680, 0], True),
    ([-2, 2, 3], True),
])
def test_examples(int_list, result):
    assert check_opposite_number_in(int_list) == result


@pytest.mark.parametrize('int_list, result', [
    ([0], True),
    ([-3, 1, 2], True),
    ([-98634, -86888, -48841, -40483, 2612, 9225, 17848, 71967, 84319, 88875], True),
])
def test_optional_bonus(int_list, result):
    assert check_adding_elements(int_list) == result
