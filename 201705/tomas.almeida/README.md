# Second @vigojug challenge by @tomasalmeida

* You should have GCC to compile this :-)
* You should have python to generate some big arrays

I am using this PC for tests

```
$ cat /proc/cpuinfo
processor   : 0
vendor_id   : GenuineIntel
cpu family  : 6
model       : 37
model name  : Intel(R) Core(TM) i5 CPU       M 430  @ 2.27GHz
stepping    : 2
microcode   : 0xe
cpu MHz     : 1330.000
```

The solutions is **mono thread** so more cpu's do not help.

## Compile

`$ gcc -g -O3 -o reto reto.c`

* `gcc` is the compiler (yes, we need to compile, soooo old)
* `-g` is for debugging purposes
* `-O3` is for GCC optimizations (it changes A LOT)
* `-o` for the name of the executable file

## Tests generation

I am using python to generate the arrays, I am not a good python programmer, so take the script without any warranty :D

`$ ./inputGenerator.py RANGE_BEGIN RANGE_END ELEMENTS`

Example:

```
$ ./inputGenerator.py -10 10 5
9 5 -3 -2 -10
```
### Tests
```
$ time ./reto  `./inputGenerator.py -10000 10000 3000`
Wow let's have fun with 3000 elements
       6       -4   10160811   -9824338
Element[     0] =    -9997 | Subsets =            1
Element[     1] =    -9995 | Subsets =            2
...
...
Element[  1969] =       -4 | Subsets =      9824304
Element[  1970] =        6 | Subsets =      9824318
Element[  1971] =        9 | Subsets =      9824327
There is a subset with sum 0!!!

real    0m45.268s
user    0m45.012s
sys     0m0.184s

```
You can play with the generator to give you more bad results :-)

Also, there is a `tester.sh` to perform quick tests. 

# License:
 GPL 3.0

 keep it free as in freedom
