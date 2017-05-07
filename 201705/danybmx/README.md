# Posible solución en Go ó reto de Maio do VigoJUG.

É unha solución sinxela e non moi eficiente pero pasa os tests!!

O meu obxectivo foi volver un pouco a Go e traballar con channels minimamente xa
que é algo que me custa entender.

Había descartado a concurrencia xa que facía que fose máis lento o proceso, pero resulta
que me acabei dando conta de que o pasar información usando channels consume moitos
recursos entón non era boa idea procesar cada operación nunha goroutine. Finalmente
dividín o número de operacións a realizar entre o numero de procesos que se van a lanzar
e calcular así en cada proceso procesos/workers.

Para executalo, soamente necesitas por os ficheiros no teu GOPATH e facer un
`go test` e se queres ver un pouca máis de información `go test -v -test.timeout 60m`.

En canto ó último bonus, foime imposible pasar de 30 en 300s... como xa dixen
non parece moi eficiente pero foi divertido!.

```
--- PASS: TestBigSet_31 (283.61s)
sumacero_test.go:182: 31 numbers processed in  283.611426081 s
```
