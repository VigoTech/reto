# -*- coding: utf-8 -*-
"""
Reto 201705.

Dada unha lista ordenada de enteiros, escribir unha función que devolva si hai
dous enteiros na lista que sumen 0.
Por exemplo, devolvería true si na lista hai os valores -14435 e 14335,
xa qué -14435 + 14335 = 0.
Tamén devolverá true si o 0 aparece na lista.
"""
import collections


def impossible_1(ordered_list, num):
    """
    Fast check of impossible cases for no-bonus challenge.

    - Minimum num in ordered_list < num.
    - ordered_list is empty

    Parameters
    ----------
    ordered_list: list of int
    num: int

    Return
    ------
    bool
    """
    try:
        if ordered_list[0] > num:
            return True
    except IndexError:
        return True


# Solver 1
def deque_solver(ordered_list, num):
    """
    Brute force approach using collections.dque.

    Parameters
    ----------
    ordered_list: list of int
    num: int

    Return
    ------
    bool
    """
    dque = collections.deque(ordered_list)
    while len(dque) > 1:
        n_min = dque[0]
        n_max = dque[-1]
        if n_min == num or n_max == num:
            return True
        elif n_min + n_max == 0:
            return True
        else:
            dque.pop()
            dque.popleft()
    if len(dque) > 0 and dque[0] == num:
        return True
    else:
        return False


def exist_subset(ordered_list, num, impossible, solver):
    """
    Wrapper for different impossible cases and solvers.

    Parameters
    ----------
    ordered_list: list of int
    num: int
    impossible: function(ordered_list, num)
        Fast check of impossible cases
    solver: function(ordered_list, num)
        Should solve all possible cases.

    Return
    ------
    bool
    """
    if impossible(ordered_list, num):
        return False
    else:
        return solver(ordered_list, num)


if __name__ == '__main__':
    import pickle
    with open("test_cases_no_bonus.pk", "rb") as f:
        test_cases = pickle.load(f)

    NUM = 0
    for case in test_cases:
        print(case[0], case[1])
        assert exist_subset(case[0], NUM, impossible_1, deque_solver) == case[1]

    print("\nAll test cases passed\n")
