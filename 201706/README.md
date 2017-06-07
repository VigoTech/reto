# Descripción

Quién nunca usó una maquina expendedora? Las famosas máquinas de vending! Juan, un programador informático, escogía entre una palmera de chocolate o unas galletas sanas y le entró la duda de como estas maquinas calculan el número de monedas de cada valor necesarias para formar el valor del cambio.

Nuestro reto de este mes es ayudar al pobre Juan a descubrir cómo calcular este número. Ah! por cierto, Juan escogió la palmera de chocolate.

## Data Límite creación de la PR

Luns 26 de junio as 21:00

## Problema base
Vamos a calcular cuántas monedas y de qué valores hay que devolver para dar el cambio correcto. Tenemos infinitas monedas de cada valor (el valor -1 indica cantidad infinita de monedas).

### Entrada
El primer valor es el número de opciones de monedas que existen en esta divisa, seguido de los valores de las monedas en orden cresciente, en otra línea recebemos el número de monedas de cada valor (en este caso -1 siempre) y por último tenemos el cambio que debemos generar.

### Salida
Cuantas monedas de cada valor debes usar para dar el cambio o -1 si no hay combinación posible.

#### Ejemplo
##### Entrada
Queremos generar un cambio de 65 centimos de euro. Sabiendo que hay 6 valores de los centimos de euro son 1, 2, 5, 10, 20 y 50.
```
6
 1  2  5 10 20 50
-1 -1 -1 -1 -1 -1
65
```

##### Salida
Una solución para el problema dado. En nuestro ejemplo, cualquiera de estas sería válida:
* 1 moneda de 50, 1 de 10 y 1 de 5.
* 3 monedas de 20 y una de 5.
* 6 monedas de 10 y una de 5.
etc.


```
50 10 5
20 20 20 5
10 10 10 10 10 10 5
```

## Bonus
Encontrar el menor numero de monedas necesarias para resolver el problema anterior. Tenemos infinitas monedas de cada valor.

### Ejemplo
```
#Euro
6
 1  2  5 10 20 50
-1 -1 -1 -1 -1 -1
187
50 50 50 20 10 5 2
```

```
#Dolar
5
 1  5 10 25 50
-1 -1 -1 -1 -1
187
50 50 50 25 10 1 1
```

```
#Pesetas
8
 1  5 10 25 50 100 200 500
-1 -1 -1 -1 -1  -1  -1  -1
187
100 50 25 10 1 1
```

## Bonus 2
Encontrar el menor numero de monedas necesarias para resolver el problema anterior. Tenemos un número limitado de monedas de cada valor.

### Ejemplo
```
#Euro
6
1 2 5 10 20 50
0 5 5  5  5  2
187
50 50 20 20 20 5 2
```

```
#Dolar
5
1 5 10 25 50
2 2 0  1  10
187
50 50 50 25 5 5 1 1
```

# Source / Fonte

Desvelarase o final do reto.
