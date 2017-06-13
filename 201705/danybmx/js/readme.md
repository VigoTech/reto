# Posible solución al segundo reto de VigoJUG en node.js

## Instalación

Es necesario tener instalado node.js en el entorno.

Una vez instalado, colocarse en la raíz del proyecto ejecutar `npm install`
para instalar las dependencias.

## Ejecución de pruebas

Una vez instaladas las dependencias, desde la misma raíz, ejecutar `npm test`

Utilizando concurrencia conseguí arañar dos número más!
```
PASS  ./sumacero.test.js (538.526s)
 ✓ Should found a zero group on this series (1007ms)
 ✓ Should not found a zero group on this series (1937ms)
 ✓ Check if it can solve a serie of 27 numbers in less than 300s (283157ms)
 ✓ Check if it can solve a serie of 29 numbers with concurrency in less than 300s (252010ms)
```

Pero la magia llegó con el truquillo de @tomas.almeida!!
```
PASS  ./sumacero.test.js (180.207s)
  ✓ Check if it can solve a serie of 40 numbers with concurrency in less than 300s (179906ms)
```
