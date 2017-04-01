# Descrición

No ano 2095, nun interesante xiro dos acontecementos, decídese que a linguaxe Basic, con máis de 50 anos, é a mellor linguaxe do universo. Ti traballas para unha compañía de nome DinoSetoSpace que recentemente foi adquirida por unha compañía chamada SirenoExpress. Mentres DinoSetoSpace ten unhas normas moi rigorosas de formateo, exactamente catro espazos por nivel de indentación, os desenvolvedores de SirenoExpress fixeron o que lles saíu do peto. O teu traballo e actualizar os proxectos de SirenoExpress os estándares de DinoSetoSpace.

## Descrición da entrada

Se te dará un número N, representando o número de liñas Basic. Seguindo esa liña, haberá outra liña co texto usado para indentar, qué será ···· para que se poda ver. Finalmente, haberá N liñas de pseudocódigo mezclando diferentes tipos de indentación (espazos e tabuladores, representados por · e » por visibilidade) que necesitan ser reindentados.

Os bloques están limitados por IF e ENDIF, e tamén por FOR e NEXT.

## Descrición da saída

A tua saía debería ser código fonte indentado polas guías de DinoSetoSpace.

# Exemplo de entrada

```
12
····
VAR I
·FOR I=1 TO 31
»»»»IF !(I MOD 3) THEN
··PRINT "FIZZ"
··»»ENDIF
»»»»····IF !(I MOD 5) THEN
»»»»··PRINT "BUZZ"
··»»»»»»ENDIF
»»»»IF (I MOD 3) && (I MOD 5) THEN
······PRINT "FIZZBUZZ"
··»»ENDIF
»»»»·NEXT
```

# Exemplo de saída

```
VAR I
FOR I=1 TO 31
····IF !(I MOD 3) THEN
········PRINT "FIZZ"
····ENDIF
····IF !(I MOD 5) THEN
········PRINT "BUZZ"
····ENDIF
····IF (I MOD 3) && (I MOD 5) THEN
········PRINT "FIZZBUZZ"
····ENDIF
NEXT
```

# Bonus

Dar un código de erro por bloques que non encaixan o liñas que faltan. Por exemplo, a esta fáltalle un ENDIF:

```
4
....
FOR I=0 TO 10
····IF I MOD 2 THEN
········PRINT I
NEXT
```

A esta fáltalle un ENDIF e un NEXT:

```
3
....
FOR I=0 TO 10
····IF I MOD 2 THEN
········PRINT I
```

Está ten un ENDIF pero non un IF e un FOR pero non un NEXT:

```
3
.....
FOR I=0 TO 10
····PRINT I
ENDIF
```

Está ten un extra ENDIF:

```
4
....
FOR I=0 TO 10
····PRINT I
NEXT
ENDIF
```
