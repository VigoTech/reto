from itertools import chain
from itertools import combinations

import numpy


def powerset(iterable, min_size):
    s = list(iterable)
    return chain.from_iterable(combinations(s, r)
                               for r in range(min_size, len(s) + 1))


def reduced_combinations(candidate, numbers):
    max_pos = numpy.searchsorted(numbers, candidate) + 1
    reduced_numbers = numbers[:max_pos]
    min_size = numpy.searchsorted(numpy.cumsum(numbers[::-1]), candidate) + 1
    return powerset(reduced_numbers, min_size=min_size)


def numbers_to_positive_negative(numbers):
    numbers = numpy.array(numbers)
    split = numpy.searchsorted(numbers, 0)
    positive = numbers[split:]
    negative = numbers[:split]
    # Rearrange negative numbers
    negative = abs(negative[::-1])
    return positive, negative


def two_sum_zero(numbers):
    if not numbers:
        return False
    positive, negative = numbers_to_positive_negative(numbers)
    # Return True if 0 is in array
    if positive[0] == 0:
        return True
    for p in range(len(positive)):
        for n in range(len(negative)):
            if positive[p] > negative[n]:
                n += 1
            elif positive[p] < negative[n]:
                p += 1
            else:
                return True
    return False


def has_subset_sum_zero(numbers):
    positive, negative = numbers_to_positive_negative(numbers)
    # Return True if 0 is in array
    if positive[0] == 0:
        return True

    # Differenciate by size
    if len(positive) > len(negative):
        bigger = positive
        smaller = negative
    else:
        smaller = positive
        bigger = negative

    # Try to reduce a bit the necessary combinations
    for c in powerset(smaller, min_size=1):
        candidate = sum(c)
        for slice_combination in reduced_combinations(candidate, bigger):
            if candidate == sum(slice_combination):
                return True
    return False
