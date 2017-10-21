#!/bin/env python3

import os
import sys
from typing import Any, Iterable, List, Optional


CAPACITY_0 = 4
CAPACITY_1 = 7

MAXIMUM_DEPTH = 10


def debug(value: Any, *, depth: Optional[int]=None) -> None:
    depth = depth + 1 if depth is not None else 0
    if os.getenv('DEBUG', 'n').upper() in ('1', 'TRUE', 'Y', 'YES'):
        indent = '  ' * depth
        print(indent + value, file=sys.stderr)


class Jar(int):
    pass


class State:
    def __init__(self, *jars: Jar) -> None:
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
        jars[self.idx] = Jar(0)
        return State(*jars)


class Fill(Action):
    def __init__(self, idx: int, capacity: int) -> None:
        self.idx = idx
        self.capacity = capacity
        self.__doc__ = f'Encher xarra {self.idx}'

    def __call__(self, state: State) -> State:
        jars = list(state.jars)
        jars[self.idx] = Jar(self.capacity)
        return State(*jars)


class Pour(Action):
    def __init__(self, donor: int, recipient: int, capacity: int) -> None:
        self.donor = donor
        self.recipient = recipient
        self.capacity = capacity
        self.__doc__ = f'Verter xarra {self.donor} en {self.recipient}'

    def __call__(self, state: State) -> State:
        # Calculate exchange
        source = state.jars[self.donor]
        destination = state.jars[self.recipient]
        space = self.capacity - destination
        exchange = source if source <= space else space
        # Do the exchange
        jars = list(state.jars)
        jars[self.donor] = Jar(source - exchange)
        jars[self.recipient] = Jar(destination + exchange)
        return State(*jars)


def build_actions(*capacities: int) -> Iterable:
    actions = []  # type: List[Action]
    for n, capacity in enumerate(capacities):
        actions.append(Fill(n, capacity))
        actions.append(Empty(n))
        for donor in range(len(capacities)):
            if donor == n:
                continue
            actions.append(Pour(donor, n, capacity))
    return actions


def test() -> None:
    empty_0 = Empty(0)
    empty_1 = Empty(1)  # noqa: F841
    fill_0 = Fill(0, CAPACITY_0)  # noqa: F841
    fill_1 = Fill(1, CAPACITY_1)
    pour_0_to_1 = Pour(0, 1, CAPACITY_1)  # noqa: F841
    pour_1_to_0 = Pour(1, 0, CAPACITY_0)
    state = State(Jar(0), Jar(0))
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
    assert 6 in state.jars
    print(f'Conseguido! {state}')


class Runner:
    def __init__(self, actions: Iterable, goal: int, depth: int) -> None:
        self.actions = tuple(actions)
        self.goal = goal
        self.depth = depth

    def check(self, state: State) -> bool:
        return self.goal in state.jars

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


def main() -> None:
    args = [int(i) for i in sys.argv[1:]]
    goal, capacities = args[0], args[1:]
    zeroes = [Jar(0) for _ in capacities]
    actions = build_actions(*capacities)
    for depth in range(1, MAXIMUM_DEPTH):
        runner = Runner(actions, goal, depth)
        chain = runner.search(State(*zeroes), [])
        if chain:
            debug(f'Found solution for depth {depth}')
            break
        debug(f'No solution for depth {depth}')
    else:
        print('No solution found.')
        raise SystemExit(1)

    # Execute algorithm with the ofund secuence
    state = State(*zeroes)
    for action in chain:
        print(action.__doc__)
        state = action(state)
    print(f'Conseguido! {state}')


if __name__ == '__main__':
    main()
