

def check_opposite_number_in(integers_list):
    for item in integers_list[:]:
        integers_list.remove(item)
        item = item * (-1)
        if item == 0 or item in integers_list:
            return True
    return False
