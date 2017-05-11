TEST
====

```sh
sbt test
```

```sh
sbt run
```

OPTIMIZATIONS
=============

- [x] custom subsets with sum (2nd version)
- [ ] split positive and negative numbers
- [ ]


RESULTS
========

1st version
--------------

Array with 24 records: elapsed time = 22568ms
Array with 25 records: elapsed time = 46773ms
Array with 26 records: elapsed time = 100266ms
Array with 27 records: elapsed time = 204190ms
Array with 28 records: elapsed time = 424639ms


2nd version
------------

Array with 303 records: elapsed time = 1422ms
Array with 304 records: elapsed time = 1445ms
Array with 305 records: elapsed time = 1474ms
Array with 306 records: elapsed time = 1467ms
Array with 307 records: elapsed time = 1511ms
Array with 308 records: elapsed time = 1510ms
Array with 309 records: elapsed time = 1564ms
