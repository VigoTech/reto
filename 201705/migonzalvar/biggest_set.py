#!/usr/bin/env python3

from contextlib import contextmanager
import time

from main import has_subset_sum_zero

class Duration:
    def __init__(self, elapsed=None):
        self.elapsed = elapsed


@contextmanager
def less_than(secs):
    duration = Duration()
    tic = time.time()
    yield duration
    elapsed = time.time() - tic
    duration.elapsed = elapsed


def nosolution_case(N):
    return range(1, N + 1)


def negative_worst_case(N):
    case = list(range(-N + 1, 0))
    case += [abs(sum(case))]
    return case


def positive_worst_case(N):
    case = list(range(1, N))
    case.insert(0, - sum(case))
    return case


def do():
    strategies = [nosolution_case, negative_worst_case, positive_worst_case]
    for strategy in strategies:
        print(f'## Using {strategy.__name__}')
        print()
        for n in range(1, 100, 10):
            source = range(1, n)
            print(f'Length: {n} items')
            with less_than(300) as duration:
                result = has_subset_sum_zero(source)
                print(f'Result: {result}')
            print(f'Duration: {duration.elapsed} seconds')
            if duration.elapsed >= secs:
                print('Limit reached. Stopping.')
                break
            print('Continue searching...')
            print()


if __name__ == '__main__':
    do()
