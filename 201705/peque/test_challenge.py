import pytest

from main import has_subset_sum_zero
from main import two_sum_zero
from main import powerset
from main import reduced_combinations


@pytest.mark.parametrize('source, result', [
    ([1, 2, 3], False),
    ([-5, -3, -1, 2, 4, 6], False),
    ([], False),
    ([-1, 1], True),
    ([-97364, -71561, -69336, 19675, 71561, 97863], True),
    ([-53974, -39140, -36561, -23935, -15680, 0], True),
    ([-2, 2, 3], True),
])
def test_two_sum_zero(source, result):
    assert two_sum_zero(source) is result


@pytest.mark.parametrize('result,source', [
    # True (simple cases)
    (True, [0]),
    (True, [-3, 1, 2]),
    (True, [-2, 2, 3]),
    (True, [-3, 0, 5, 7]),
    (True, [-9, -8, -7, -6, -5, -4, -3, -2, -1, 45]),
    (True, [-45, 1, 2, 3, 4, 5, 6, 7, 8, 9]),
    # False (simple cases)
    (False, [1, 2, 3]),
    (False, [-3, 5, 7]),
    # False
    (False, [-83314, -82838, -80120, -63468, -62478, -59378, -56958, -50061, -34791, -32264, -21928, -14988, 23767, 24417, 26403, 26511, 36399, 78055]),
    (False, [-92953, -91613, -89733, -50673, -16067, -9172, 8852, 30883, 46690, 46968, 56772, 58703, 59150, 78476, 84413, 90106, 94777, 95148]),
    (False, [-94624, -86776, -85833, -80822, -71902, -54562, -38638, -26483, -20207, -1290, 12414, 12627, 19509, 30894, 32505, 46825, 50321, 69294]),
    (False, [-83964, -81834, -78386, -70497, -69357, -61867, -49127, -47916, -38361, -35772, -29803, -15343, 6918, 19662, 44614, 66049, 93789, 95405]),
    (False, [-68808, -58968, -45958, -36013, -32810, -28726, -13488, 3986, 26342, 29245, 30686, 47966, 58352, 68610, 74533, 77939, 80520, 87195]),
    # True
    (True, [-97162, -95761, -94672, -87254, -57207, -22163, -20207, -1753, 11646, 13652, 14572, 30580, 52502, 64282, 74896, 83730, 89889, 92200]),
    (True, [-93976, -93807, -64604, -59939, -44394, -36454, -34635, -16483, 267, 3245, 8031, 10622, 44815, 46829, 61689, 65756, 69220, 70121]),
    (True, [-92474, -61685, -55348, -42019, -35902, -7815, -5579, 4490, 14778, 19399, 34202, 46624, 55800, 57719, 60260, 71511, 75665, 82754]),
    (True, [-85029, -84549, -82646, -80493, -73373, -57478, -56711, -42456, -38923, -29277, -3685, -3164, 26863, 29890, 37187, 46607, 69300, 84808]),
    (True, [-87565, -71009, -49312, -47554, -27197, 905, 2839, 8657, 14622, 32217, 35567, 38470, 46885, 59236, 64704, 82944, 86902, 90487]),
])
def test_has_subset_sum_zero(result, source):
    assert has_subset_sum_zero(source) is result


@pytest.mark.parametrize('numbers,min_size,result', [
    ([1,2,3], 1, [(1,), (2,), (3,), (1,2), (1,3), (2,3), (1,2,3)]),
    ([1,2,3], 2, [(1,2), (1,3), (2,3), (1,2,3)]),
])
def test_powerset(numbers, min_size, result):
    assert list(powerset(numbers, min_size=min_size)) == result


@pytest.mark.parametrize('candidate,numbers,result', [
    (2, [1, 2, 3, 4, 5], powerset([1, 2], min_size=1)),
    (8, [1, 2, 3, 4, 5], powerset([1, 2, 3, 4, 5], min_size=2)),
    (13, [1, 2, 3, 4, 5], powerset([1, 2, 3, 4, 5], min_size=4)),
])
def test_reduced_combinations(candidate, numbers, result):
    assert list(reduced_combinations(candidate, numbers)) == list(result)
