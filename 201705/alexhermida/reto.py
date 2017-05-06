from itertools import combinations


def check_opposite_number_in(integers_list):
    for item in integers_list[:]:
        integers_list.remove(item)
        item = item * (-1)
        if item == 0 or item in integers_list:
            return True
    return False


def check_adding_elements(integers_list):
    if [item for i in range(len(integers_list), 0, -1) for item in
            combinations(integers_list, i) if sum(item) == 0]:
        return True

    return False
