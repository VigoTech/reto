# Modeling of the bucket verting problem as an M-SET orbit

## Background
A monoid M is a set with an associative multiplication operation m and an identity id operation.
`a*(b*c) = (a*b)*c`
`a*id = id*a`

An M-Set is a SET with a monoid M acting on it
`a: M x S -> S`
such that:
```
          m x 1
M x M x S ----> M x S
1 x a|            | a
     v            v
  M x S  ----->   S
           a

   {id}x1     m
S ---> M x S ---> S is the equality.
```

## The model
The number of jugs give us the dimensions of our space.
Eg. for two jugs we are `Z^2`, for n jugs we are in `Z^n`.

The jug sizes give us the upper bound in each dimension.
Eg. for jug sizes 4 and 7 our space defined by:
```
S = {(x,y) in Z x Z |  x>=0 , y>=0, x<=jugSize1, y<=jugSize2}
```

The monoid M is a freely generated monoid from following operations:
```
opFill{pos} :  Fill coordinate  pos to the maximum
opEmpty{pos}:  Empty coordinate pos to 0
opVert{posInitial, posFinal}: vert coordinate posInitial into posFinal
```
The identity element of `M` is the "do nothing operation".

## The problem
Start with the origin element `O=(0,...,0)` in our space S.
Calculate the orbit of `O` under the action of `M`.
The set of possible solutions is given by `SOL = {(x1,...,xn) in Z^n| x1=requestedValue or x2=requestedValue ...or xn=requestedValue}`.
If `O intersection SOL` is not empty then the problem is solvable.

## Obtaining a solution.
As we are calculating the orbit of `O` check whether any coordinate coincides with the requested value.
If such an element exists, the path of operations give us the sequence of operations required to obtain the desired result.
