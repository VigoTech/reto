from itertools import accumulate


def check_opposite_number_in(integers_list):
    for item in integers_list[:]:
        integers_list.remove(item)
        item = item * (-1)
        if item == 0 or item in integers_list:
            return True
    return False


def check_adding_elements(integers_list):
    list_copy = integers_list[:]

    if 0 in list(accumulate(list_copy)):
        return True

    for item in list_copy:
        if check_opposite_number_in(item, integers_list):
            return True
