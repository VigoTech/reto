#!/bin/env python3

import os
import sys
from typing import Any, Iterable, List, Optional, Tuple


CAPACITY_0 = 4
CAPACITY_1 = 7

MAXIMUM_DEPTH = 10

# Utils


def debug(value: Any, *, depth: Optional[int]=None) -> None:
    depth = depth + 1 if depth is not None else 0
    if os.getenv('DEBUG', 'n').upper() in ('1', 'TRUE', 'Y', 'YES'):
        indent = '  ' * depth
        print(indent + value, file=sys.stderr)


# Model


class Jar:
    def __init__(self, value: int, capacity: int) -> None:
        self._value = value
        self._capacity = capacity

    @property
    def capacity(self) -> int:
        return self._capacity

    @property
    def value(self) -> int:
        return self._value

    def __add__(self, value: int) -> None:
        self._value += value

    def __sub__(self, value: int) -> None:
        self._value -= value

    def __eq__(self, other: Any) -> bool:
        return (self.value, self.capacity) == (other.value, other.capacity)

    def __str__(self) -> str:
        return f'{self.value} litros'

    def empty(self) -> 'Jar':
        return Jar(0, self.capacity)

    def fill(self) -> 'Jar':
        return Jar(self.capacity, self.capacity)

    def put(self, value: int) -> 'Jar':
        return Jar(value, self.capacity)


class State:
    def __init__(self, *jars: Jar) -> None:
        self.jars = jars

    def get_jar(self, idx: int) -> Jar:
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
        tpl = 'Xarra {idx}: {value}'
        fragments = self._repr(tpl)
        return ', '.join(fragments)

    def __repr__(self) -> str:
        tpl = 'jar_{idx}={value}'
        fragments = self._repr(tpl)
        value = ', '.join(fragments)
        return f'State({value})'


def get_initial_state(*capacities: int) -> State:
    return State(*[Jar(0, capacity) for capacity in capacities])


# Actions


class Action:
    def __call__(self, state: State) -> State:
        raise NotImplementedError


class Empty(Action):
    def __init__(self, idx: int) -> None:
        self.idx = idx
        self.__doc__ = f'Vaciar xarra {self.idx}'

    def __call__(self, state: State) -> State:
        jars = list(state.jars)
        jars[self.idx] = jars[self.idx].empty()
        return State(*jars)


class Fill(Action):
    def __init__(self, idx: int) -> None:
        self.idx = idx
        self.__doc__ = f'Encher xarra {self.idx}'

    def __call__(self, state: State) -> State:
        jars = list(state.jars)
        jars[self.idx] = jars[self.idx].fill()
        return State(*jars)


class Pour(Action):
    def __init__(self, donor: int, recipient: int) -> None:
        self.donor = donor
        self.recipient = recipient
        self.__doc__ = f'Verter xarra {self.donor} en {self.recipient}'

    def __call__(self, state: State) -> State:
        # Calculate exchange
        source = state.jars[self.donor]
        destination = state.jars[self.recipient]
        space = destination.capacity - destination.value
        exchange = source.value if source.value <= space else space
        # Do the exchange
        jars = list(state.jars)
        jars[self.donor] = source.put(source.value - exchange)
        jars[self.recipient] = destination.put(destination.value + exchange)
        return State(*jars)


def build_actions(number_of_jars: int) -> Iterable:
    actions = []  # type: List[Action]
    for n in range(number_of_jars):
        actions.append(Fill(n))
        actions.append(Empty(n))
        for donor in range(number_of_jars):
            if donor == n:
                continue
            actions.append(Pour(donor, n))
    return actions


# Test


def test() -> None:
    empty_0 = Empty(0)
    # empty_1 = Empty(1)  # noqa: F841
    # fill_0 = Fill(0)  # noqa: F841
    fill_1 = Fill(1)
    # pour_0_to_1 = Pour(0, 1)  # noqa: F841
    pour_1_to_0 = Pour(1, 0)
    state = State(Jar(0, CAPACITY_0), Jar(0, CAPACITY_1))
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
    assert Jar(6, 7) in state.jars
    print(f'Conseguido! {state}')


# Algorithm


class Runner:
    def __init__(self, actions: Iterable, goal: int, depth: int) -> None:
        self.actions = tuple(actions)
        self.goal = goal
        self.depth = depth

    def check(self, state: State) -> bool:
        return self.goal in (jar.value for jar in state.jars)

    def search(
            self,
            state: State,
            visited: List[State],
            *,
            depth: int=0) -> Optional[List]:

        if state in visited:
            # Already visited
            debug(f'Already visited {state!r}', depth=depth)
            return None

        if depth >= self.depth:
            # Maximum depth reached
            debug('Maximum depth reached', depth=depth)
            return None

        results = [action(state) for action in self.actions]

        # Check if any of the new states is the goal
        solutions = [self.check(new_state) for new_state in results]
        if any(solutions):
            idx = solutions.index(True)
            action = self.actions[idx]
            return [action]

        # No solution yet, continue searching
        for action, new_state in zip(self.actions, results):
            # Let's continue with the search
            visited = [state] + visited
            result = self.search(new_state, visited, depth=depth+1)
            if result:
                debug('Coming back', depth=depth)
                return [action] + result

        # Not found
        debug('Not found!', depth=depth)
        return None


# Main

def parse() -> Tuple[int, List[int]]:
    args = [int(i) for i in sys.argv[1:]]
    goal, capacities = args[0], args[1:]
    return goal, capacities


def run(goal: int, *capacities: int) -> None:
    # Init problem
    initial_state = get_initial_state(*capacities)
    actions = build_actions(len(capacities))

    # Run algorithm from 1 to MAXIMUM_DEPTH
    for depth in range(1, MAXIMUM_DEPTH):
        runner = Runner(actions, goal, depth)
        chain = runner.search(initial_state, [])
        if chain:
            debug(f'Found solution for depth {depth}')
            break
        debug(f'No solution for depth {depth}')
    else:
        print('No solution found.')
        raise SystemExit(1)

    # Execute algorithm with the found secuence
    state = initial_state
    for action in chain:
        print(action.__doc__)
        state = action(state)
    print(f'Conseguido! {state}')


def main() -> None:
    goal, capacities = parse()
    run(goal, *capacities)


if __name__ == '__main__':
    main()
