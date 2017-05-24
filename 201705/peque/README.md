Reto JUG Mayo 2017
==================

**Gracias a Miguel González por su implementación en Python!** :heart:

Sólo implemento la solución de la segunda parte. Intento reducir un poco
el número de combinaciones generadas cuando sea posible.

Dados los tests "worst case" propuestos calcula la solución para
`N = 10 000 000` en un par de segundos, ya que en realidad son muy simples
de reducir.


Explicación
===========

Básicamente divido en primer lugar positivos y negativos. La idea es que no
es necesario, a priori, probar todas las combinaciones posibles (combinaciones
de números positivos no van a dar nunca 0, tampoco para combinaciones de
números negativos). Creo entonces dos vectores ordenados, ambos con números
naturales (cambio el signo al vector negativo).

Una vez separados, cojo el vector más pequeño de los dos (tendrá como máximo
`N / 2` elementos). De este sí empiezo a generar todas las combinaciones
posibles (aunque también podría reducirse de forma parecida a como indico a
continuación en la reducción del segundo vector).

Para cada combinación del vector más pequeño, calculo la suma y es entonces
cuando intento reducir el número de combinaciones necesarias del otro vector.
Las reducciones son las siguientes:

- Llamando a la suma del primero `S`, podemos reducir el número de
  combinaciones del segundo vector recortando todos los números mayores que
  `S` (no hay combinación posible que vaya a dar `S`).
- Por otro lado miro el mínimo número de elementos que debemos combinar del
  segundo vector para sumar `S` (si el segundo vector es `[1, 2, 3, 4]`, para
  sumar `6` necesitamos combinaciones de 2 números como mínimo (`4 + 3 = 7`).

Con la división inicial y con estas simples reglas de reducción se consigue
mejorar mucho el rendimiento para casos típicos/medios/aleatorios. También
para casos "sintéticos" como `[-10, 1, 2, 3, 4]`, ya que son muy fácilmente
reducibles.

El caso peor de todos para esta solución es difícil de generar (más que la
propia solución del problema), así que no lo he buscado. En todo caso debería
seguir siendo mejor que una solución "por fuerza bruta".

Si alguien tiene vectores interesantes para poner a prueba esta solución
estaré encantado de probarlos! ^^


Otras reducciones
=================

Como comento, se puede reducir también el número de combinaciones generadas
sobre el vector más pequeño de forma similar a como lo hemos hecho con el
grande.

Además se puede acelerar calculando a priori los límites mínimo y máximo de
la suma de los valores del segundo vector. Así, tras generar una combinación
sobre el primero y hacer la suma, podemos directamente descartar en caso de
que no se encuentre en ese rango.

Tiene pinta de que quizás se pueda también limitar el número de combinaciones
del primero con una función que tenga en cuenta los límites del segundo. Por
ejemplo: que deje de generar combinaciones cuando sepa que las que quedan van
a ser todas con una suma superior al límite máximo del segundo vector. Esta
misma función podría aplicarse a la generación de combinaciones reducidas del
segundo vector también. No he llegado a pensar en cómo implementar esto, pero
supongo que es posible hacerlo.
