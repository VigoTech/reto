# Descrición

Dada unha lista ordenada de distintos enteiros, escribir unha función que devolva si hai dous enteiros na lista que sumen 0. Por exemplo, devolvería `true` si na lista hai os valores -14435 e 14335, xa qué -14435 + 14335 = 0. Tamén devolverá `true` si o 0 aparece na lista.

## Data Límite creación da PR

Luns 29 de Maio as 21:00

## Exemplos

```
[1, 2, 3] -> false
[-5, -3, -1, 2, 4, 6] -> false
[] -> false
[-1, 1] -> true
[-97364, -71561, -69336, 19675, 71561, 97863] -> true
[-53974, -39140, -36561, -23935, -15680, 0] -> true
[-2, 2, 3] -> true
```

## Bonus opcional

O reto de este mes é unha versión simplificada do problema de sumar subconxuntos. O bonus consiste en resolver o problema completo. Dada unha lista ordeada de distintos enteiros, escribir unha función que devolva si hai algun subconxunto non-vacío de enteiros que sumen 0.

Exemplos de subconxuntos que suman 0:

```
[0]
[-3, 1, 2]
[-98634, -86888, -48841, -40483, 2612, 9225, 17848, 71967, 84319, 88875]
```

É dicir, se calquera deses conxuntos aparece na entrada, debería devolver `true`.

Nota: o exemplo `[-5, -3, -1, 2, 4, 6] -> false` do reto pasa a `true` co bonus.

## Exemplos do bonus

Os seguintes deberían devolver `false`:

```
[-83314, -82838, -80120, -63468, -62478, -59378, -56958, -50061, -34791, -32264, -21928, -14988, 23767, 24417, 26403, 26511, 36399, 78055]
[-92953, -91613, -89733, -50673, -16067, -9172, 8852, 30883, 46690, 46968, 56772, 58703, 59150, 78476, 84413, 90106, 94777, 95148]
[-94624, -86776, -85833, -80822, -71902, -54562, -38638, -26483, -20207, -1290, 12414, 12627, 19509, 30894, 32505, 46825, 50321, 69294]
[-83964, -81834, -78386, -70497, -69357, -61867, -49127, -47916, -38361, -35772, -29803, -15343, 6918, 19662, 44614, 66049, 93789, 95405]
[-68808, -58968, -45958, -36013, -32810, -28726, -13488, 3986, 26342, 29245, 30686, 47966, 58352, 68610, 74533, 77939, 80520, 87195]
```

Os seguintes deberían devolver `true`:

```
[-97162, -95761, -94672, -87254, -57207, -22163, -20207, -1753, 11646, 13652, 14572, 30580, 52502, 64282, 74896, 83730, 89889, 92200]
[-93976, -93807, -64604, -59939, -44394, -36454, -34635, -16483, 267, 3245, 8031, 10622, 44815, 46829, 61689, 65756, 69220, 70121]
[-92474, -61685, -55348, -42019, -35902, -7815, -5579, 4490, 14778, 19399, 34202, 46624, 55800, 57719, 60260, 71511, 75665, 82754]
[-85029, -84549, -82646, -80493, -73373, -57478, -56711, -42456, -38923, -29277, -3685, -3164, 26863, 29890, 37187, 46607, 69300, 84808]
[-87565, -71009, -49312, -47554, -27197, 905, 2839, 8657, 14622, 32217, 35567, 38470, 46885, 59236, 64704, 82944, 86902, 90487]
```

## Bonus opcional 2

Calcula o maior conxunto que a tua solución é capaz de procesar no peor caso posible en menos de 300 segundos. Engádeo como proba unitaria e publica nun README.md o dato.

Exemplos de probas que podes empregar para cubrir diferentes casos:

```
# No solution case
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10] -> false

# Negative worst case
[-9, -8, -7, -6, -5, -4, -3, -2, -1, 45] -> true

# Positive worst case
[-45, 1, 2, 3, 4, 5, 6, 7, 8, 9] -> true
```

Exemplo de código para xerar os casos anteriores en Python:

```python
def nosolution_case(N):
    return range(1, N + 1)

def negative_worst_case(N):
    case = list(range(-N + 1, 0))
    case += [abs(sum(case))]
    return case

def positive_worst_case(N):
    case = list(range(1, N))
    case.insert(0, - sum(case))
    return case
```

Aumenta o número de forma consecutiva ata atopar o límite da túa solución:

```
# *_case(11)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11] -> false
[-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 55] -> true
[-55, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10] -> true

# *_case(12)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12] -> false
[-11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 66] -> true
[-66, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11] -> true

...

```

# Source / Fonte

Desvelarase o final do reto.
