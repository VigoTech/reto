Reto JUG Maio 2017
==================

```
$ python3.6 -m venv .venv
$ source .venv/bin/activate
(.venv) $ pytest test.py -v
============================= test session starts =============================
platform linux -- Python 3.6.0, pytest-3.0.7, py-1.4.33, pluggy-0.4.0 -- /home/migonzalvar/Devel/migonzalvar/misc/vigojug/reto/201705/migonzalvar/.venv/bin/python3.6
cachedir: .cache
rootdir: /home/migonzalvar/Devel/migonzalvar/misc/vigojug/reto/201705/migonzalvar, inifile:
collected 17 items

test.py::test_acceptance_first[source0-False] PASSED
test.py::test_acceptance_first[source1-False] PASSED
test.py::test_acceptance_first[source2-False] PASSED
test.py::test_acceptance_first[source3-True] PASSED
test.py::test_acceptance_first[source4-True] PASSED
test.py::test_acceptance_first[source5-True] PASSED
test.py::test_acceptance_first[source6-True] PASSED
test.py::test_bonus[False-source0] PASSED
test.py::test_bonus[False-source1] PASSED
test.py::test_bonus[False-source2] PASSED
test.py::test_bonus[False-source3] PASSED
test.py::test_bonus[False-source4] PASSED
test.py::test_bonus[True-source5] PASSED
test.py::test_bonus[True-source6] PASSED
test.py::test_bonus[True-source7] PASSED
test.py::test_bonus[True-source8] PASSED
test.py::test_bonus[True-source9] PASSED

========================== 17 passed in 0.55 seconds ==========================
```

Solución con _truco_ pois emprega a completísima biblioteca estándar de Python, concreatamente a función `combinations` do paquete `itertools` (ver a [documentación](https://docs.python.org/3/library/itertools.html#itertools.combinations))


Con relación ao **Bonus opcional 2**, que pedía:

> Calcula o maior conxunto que a tua solución é capaz de procesar en menos de 300 segundos. Añadeo como proba unitaria e publica nun README.md o dato.

Co meu algoritmo o límite estivo na memoria non en tempo de proceso. Conseguín calcular listas de 130 millóns de elementos (~ 1e8) en msnos de 30 segundos ata esgotar a memoria.

```
(.venv) $ ./biggest_set.py
...

Length: 120000000 items
Result: True
Duration: 3.7151079177856445 seconds
Continue...

Length: 130000000 items
Result: True
Duration: 24.811022996902466 seconds
Continue...

Length: 140000000 items
[1]    11733 killed     ./biggest_set.py
```
