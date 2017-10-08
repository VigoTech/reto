CAPACITY_0 = 4
CAPACITY_1 = 7


class State:
    def __init__(self) -> None:
        self.jar_0 = 0
        self.jar_1 = 0

    def __str__(self) -> str:
        return f'Xarra 0: {self.jar_0} litros, Xarra 1: {self.jar_1} litros'


# Actions

def fill_0(state: State) -> State:
    "Encher xarra 0"
    state.jar_0 = CAPACITY_0
    return state


def fill_1(state: State) -> State:
    "Encher xarra 1"
    state.jar_1 = CAPACITY_1
    return state

def empty_0(state: State) -> State:
    "Vaciar xarra 0"
    state.jar_0 = 0
    return state


def empty_1(state: State) -> State:
    "Vaciar xarra 1"
    state.jar_1 = 0
    return state


def pour_0_to_1(state: State) -> State:
    "Verter xarra 0 en 1"
    space = CAPACITY_1 - state.jar_1
    exchange = state.jar_0 if state.jar_0 <= space else space
    state.jar_1 += exchange
    state.jar_0 -= exchange
    return state


def pour_1_to_0(state: State) -> State:
    "Verter xarra 1 en 0"
    space = CAPACITY_0 - state.jar_0
    exchange = state.jar_1 if state.jar_1 <= space else space
    state.jar_0 += exchange
    state.jar_1 -= exchange
    return state


def main() -> None:
    state = State()
    actions = (
        fill_1,
        pour_1_to_0,
        empty_0,
        pour_1_to_0,
        fill_1,
        pour_1_to_0,
    )
    for action in actions:
        print(action.__doc__)
        action(state)
        if check(state):
            print(f'Conseguido! {state}')
            break


def check(state: State) -> bool:
    return state.jar_0 == 6 or state.jar_1 == 6


if __name__ == '__main__':
    main()
