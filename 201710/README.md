# Descrición

O reto consiste en modelar o seguinte escenario:

* Tes un grifo de auga.
* Duas xarras.

Tes as seguintes accións posibles:

* Encher unha xarra.
* Vaciar unha xarra.
* Pasar o contido dun xarra a outra, ata encher a xarra destino.

O reto consiste en modelar un programa que recibe unha cantidade específica e a capacidade das xarras e imprime as accións necesarias para que nunha das xarras consigamos ter a cantidade específica.

No reto básico, temos duas xarras de 4 e 7 litros, e temos que conseguir ter unha xarra con 6 litros. O programa non receve ningún parámetro e a lóxica pode ser hard-codeada, non fai falta facer o cálculo, hai unha solución no exemplo.

## Data Límite creación da PR

Martes 31 de Outubro as 21:00

## Exemplo

Xarra 0: 4 litros
Xarra 1: 7 litros
Cantidade final: 6 litros

```
Encher xarra 1
Verter xarra 1 en 0
Vaciar xarra 0
Verter xarra 1 en 0
Encher xarra 1
Verter xarra 1 en 0
Conseguido! Xarra 0: 4 litros, Xarra 1: 6 litros
```

O contido das xarras para cada paso:

- Xarra 0: 0, Xarra 1: 7
- Xarra 0: 4, Xarra 1: 3
- Xarra 0: 0, Xarra 1: 3
- Xarra 0: 3, Xarra 1: 0
- Xarra 0: 3, Xarra 1: 7
- Xarra 0: 4, Xarra 1: 6

## Bonus opcional 1

Xeneralizar a solución para a cantidade final e a capacidade das tazas se reciba coma parámetro. Se é imposible (ou leva demasiado tempo), imprimirase unha mensaxe de erro. Salvo erro, a saída será a mesma que para o reto básico.

## Bonus opcional 2

Xeneralizar a solución para un número variable de tazas, que tamén se pasará coma parámetro.

## Nota

O reto pódese actualizar con nova información, mais exemplos, etc. Estade atentos ;-)

# Source / Fonte

Desvelarase o final do reto.
