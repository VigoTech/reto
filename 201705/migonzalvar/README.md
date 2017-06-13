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

Unha primeira aproximacióin sitúa o límiete por debaixo dos 30 elementos:

```
(.venv) $ ./biggest_set.py
Length: 1 items
Result: False
Duration: 1.4066696166992188e-05 seconds
Continue...

Length: 11 items
Result: False
Duration: 0.0007650852203369141 seconds
Continue...

Length: 21 items
Result: False
Duration: 0.2948458194732666 seconds
Continue...

Length: 31 items
Result: False
Duration: 389.0838415622711 seconds
Limit reached. Stopping.
```

----

Actualización 7-may. A proba para atopar límite para 300 segundos de execución non era correcta porque os conxuntos usados tiñan solución a primeira.
