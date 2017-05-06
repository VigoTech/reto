from itertools import combinations


def check_adding_elements(integers_list):
    if [item for i in range(len(integers_list), 0, -1) for item in
            combinations(integers_list, i) if sum(item) == 0]:
        return True

    return False
