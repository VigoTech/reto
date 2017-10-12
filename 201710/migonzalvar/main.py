from functools import partial as builtin_partial
import os
import sys
from typing import Any, Callable, Iterable, List, Optional


CAPACITY_0 = 4
CAPACITY_1 = 7

MAXIMUM_DEPTH = 6


def debug(value: Any) -> None:
    if os.getenv('DEBUG', 'n').upper() in ('1', 'TRUE', 'Y', 'YES'):
        print(value, file=sys.stderr)


class State:
    def __init__(self, *jars: int) -> None:
        self.jars = jars

    def get_jar(self, idx: int) -> int:
        return self.jars[idx]

    def __getattr__(self, name: str) -> Any:
        _, sep, suffix = name.partition('jar_')
        if sep == 'jar_':
            try:
                idx = int(suffix)
            except (ValueError, TypeError):
                pass
            else:
                return self.get_jar(idx)

        raise AttributeError(name)

    def __eq__(self, other: Any) -> bool:
        return bool(self.jars == other.jars)

    def _repr(self, tpl: str) -> List[str]:
        fragments = []
        for idx, value in enumerate(self.jars):
            line = tpl.format(idx=idx, value=value)
            fragments.append(line)
        return fragments

    def __str__(self) -> str:
        tpl = 'Xarra {idx}: {value} litros'
        fragments = self._repr(tpl)
        return ', '.join(fragments)

    def __repr__(self) -> str:
        tpl = 'jar_{idx}={value}'
        fragments = self._repr(tpl)
        value = ', '.join(fragments)
        return f'State({value})'


def check(state: State) -> bool:
    return bool(state.jar_0 == 6 or state.jar_1 == 6)


# Actions

def generic_fill_0(capacity: int, state: State) -> State:
    "Encher xarra 0"
    return State(capacity, state.jar_1)


def generic_fill_1(capacity: int, state: State) -> State:
    "Encher xarra 1"
    return State(state.jar_0, capacity)


def generic_pour_0_to_1(capacity: int, state: State) -> State:
    "Verter xarra 0 en 1"
    space = capacity - state.jar_1
    exchange = state.jar_0 if state.jar_0 <= space else space
    return State(state.jar_0 - exchange, state.jar_1 + exchange)


def generic_pour_1_to_0(capacity: int, state: State) -> State:
    "Verter xarra 1 en 0"
    space = capacity - state.jar_0
    exchange = state.jar_1 if state.jar_1 <= space else space
    return State(state.jar_0 + exchange, state.jar_1 - exchange)


def partial(func: Callable, *args, **kwargs):  # type: ignore
    wrap = builtin_partial(func, *args, **kwargs)
    wrap.__doc__ = func.__doc__
    return wrap


class Empty:
    def __init__(self, idx: int) -> None:
        self.idx = idx
        self.__doc__ = f'Vaciar xarra {self.idx}'

    def __call__(self, state: State) -> State:
        jars = list(state.jars)
        jars[self.idx] = 0
        return State(*jars)


fill_0 = partial(generic_fill_0, CAPACITY_0)
fill_1 = partial(generic_fill_1, CAPACITY_1)
pour_0_to_1 = partial(generic_pour_0_to_1, CAPACITY_1)
pour_1_to_0 = partial(generic_pour_1_to_0, CAPACITY_0)


def build_actions(capacity_0: int, capacity_1: int) -> Iterable:
    actions = (
        partial(generic_fill_0, capacity_0),
        partial(generic_fill_1, capacity_1),
        Empty(0),
        Empty(1),
        partial(generic_pour_0_to_1, capacity_1),
        partial(generic_pour_1_to_0, capacity_0),
    )
    return actions


def test() -> None:
    empty_0 = Empty(0)
    empty_1 = Empty(1)  # noqa: F841
    state = State(0, 0)
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


class Runner:
    def __init__(self, actions: Iterable) -> None:
        self.actions = actions

    def search(
            self,
            state: State,
            visited: List[State],
            *,
            depth: int=0) -> Optional[List]:
        if state in visited:
            # Already visited
            debug(f'Already visited {state!r}')
            return None

        candidates = []
        for action in self.actions:
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
            result = self.search(new_state, visited, depth=depth+1)
            if not result:
                debug('Nothing to see')
            else:
                debug('Coming back')
                return [action] + result

        # Not found
        debug('Not found!')
        return None


def main() -> None:
    capacities = map(int, sys.argv[1:])
    actions = build_actions(*capacities)
    runner = Runner(actions)
    chain = runner.search(State(0, 0), [])
    if not chain:
        print('No solution found.')
        raise SystemExit(1)

    # Execute algorithm with secuence found
    state = State(0, 0)
    for action in chain:
        print(action.__doc__)
        state = action(state)
    print(f'Conseguido! {state}')


if __name__ == '__main__':
    main()
