from itertools import combinations


def two_sum_zero(numbers):
    if 0 in numbers:
        return True
    for case in combinations(numbers, 2):
        if sum(case) == 0:
            return True
    return False


def has_subset_sum_zero(numbers):
    length = len(numbers)
    for r in range(1, length):
        for case in combinations(numbers, r):
            if sum(case) == 0:
                return True
    return False
