# -*- coding: utf-8 -*-
"""
- Reto 201705.

Dada unha lista ordenada de enteiros, escribir unha función que devolva si hai
dous enteiros na lista que sumen 0.
Por exemplo, devolvería true si na lista hai os valores -14435 e 14335,
xa qué -14435 + 14335 = 0.
Tamén devolverá true si o 0 aparece na lista.

- Bonus opcional

O reto de este mes é unha versión simplificada do problema de sumar
subconxuntos. O bonus consiste en resolver o problema completo.
Dada unha lista de distintos enteiros, escribir unha función que devolva si hai
algun subconxunto non-vacío de enteiros que sumen 0.
"""
import collections


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

        summed = n_min + n_max

        if summed == num:
            return True
        elif summed > num:
            dque.pop()
        else:
            dque.popleft()

    # worst case
    if len(dque) > 0 and dque[0] == num:
        return True
    else:
        return False


if __name__ == '__main__':
    import pickle
    with open("test_cases_no_bonus.pk", "rb") as f:
        test_cases = pickle.load(f)

    N = 0
    for case in test_cases:
        ordered_list = case[0]
        true_output = case[1]
        print("\nCASE: {}".format(ordered_list))
        print("EXPECTED VALUE: {}".format(true_output))
        our_output = deque_solver(ordered_list, N)
        assert our_output == true_output

    print("\nAll test cases passed\n")
