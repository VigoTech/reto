import os
import sys
from typing import Any, List, Optional


CAPACITY_0 = 4
CAPACITY_1 = 7

MAXIMUM_DEPTH = 6


def debug(value: Any) -> None:
    if os.getenv('DEBUG', 'n').upper() in ('1', 'TRUE', 'Y', 'YES'):
        print(value, file=sys.stderr)


class State:
    def __init__(self, jar_0: int=0, jar_1: int=0) -> None:
        self._jar_0 = jar_0
        self._jar_1 = jar_1

    @property
    def jar_0(self) -> int:
        return self._jar_0

    @property
    def jar_1(self) -> int:
        return self._jar_1

    def __eq__(self, other: Any) -> bool:
        return (self._jar_0, self._jar_1) == (other.jar_0, other.jar_1)

    def __str__(self) -> str:
        return f'Xarra 0: {self.jar_0} litros, Xarra 1: {self.jar_1} litros'

    def __repr__(self) -> str:
        return f'State(jar_0={self.jar_0}, jar_1={self.jar_1})'


def check(state: State) -> bool:
    return state.jar_0 == 6 or state.jar_1 == 6


# Actions

def fill_0(state: State) -> State:
    "Encher xarra 0"
    return State(CAPACITY_0, state.jar_1)


def fill_1(state: State) -> State:
    "Encher xarra 1"
    return State(state.jar_0, CAPACITY_1)


def empty_0(state: State) -> State:
    "Vaciar xarra 0"
    return State(0, state.jar_1)


def empty_1(state: State) -> State:
    "Vaciar xarra 1"
    return State(state.jar_0, 0)


def pour_0_to_1(state: State) -> State:
    "Verter xarra 0 en 1"
    space = CAPACITY_1 - state.jar_1
    exchange = state.jar_0 if state.jar_0 <= space else space
    return State(state.jar_0 - exchange, state.jar_1 + exchange)


def pour_1_to_0(state: State) -> State:
    "Verter xarra 1 en 0"
    space = CAPACITY_0 - state.jar_0
    exchange = state.jar_1 if state.jar_1 <= space else space
    return State(state.jar_0 + exchange, state.jar_1 - exchange)


ACTIONS = fill_0, fill_1, empty_0, empty_1, pour_0_to_1, pour_1_to_0


def test() -> None:
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
        state = action(state)
    assert check(state)
    print(f'Conseguido! {state}')


def search(state: State, visited: List[State], *, depth: int=0) -> Optional[List]:
    if state in visited:
        # Already visited
        debug(f'Already visited {state!r}')
        return None

    candidates = []
    for action in ACTIONS:
        new_state = action(state)
        if new_state == state:
            # No way out
            debug('No way out')
            continue
        if check(new_state):
            # Solution found!
            debug('Found it!')
            return [action]

        # Continue explore later
        candidates.append((action, new_state))

    if depth >= MAXIMUM_DEPTH:
        # Maximum depth reached
        debug('Maximum depth reached')
        return None

    for action, new_state in candidates:
        visited = [state] + visited
        result = search(new_state, visited, depth=depth+1)
        if not result:
            debug('Nothing to see')
        else:
            debug('Coming back')
            return [action] + result

    # Not found
    debug('Not found!')
    return None


def main() -> None:
    chain = search(State(), [])
    if not chain:
        print('No solution found.')
        raise SystemExit(1)

    # Execute algorithm with secuence found
    state = State()
    for action in chain:
        print(action.__doc__)
        state = action(state)
    print(f'Conseguido! {state}')


if __name__ == '__main__':
    main()
