Solution with Kotlin to the 1st @VigoJUG Challenge
==================================================

This is a solution implemented in Kotlin showcasing some of the language
features for educational purposes:

* Main function outside the class
* Extension functions, adding functions to the String class
* Single expression functions
* for() with ranges
* Control flow with when()
* Protection against nulls showing the usage of "?.", "!!" and the "?:" (Elvis)
  operators
* String templates
* Usage of the != and == operators with Strings
* Java interoperability using a java.util.LinkedList

It includes a gradle build file, the gradle wrapper and JUnit tests, so it can
be used as a template for other Kotlin projects.

Build
=====
Run from the project root:
```
./gradlew jar
```
It generates a jar in build/libs/indent.jar.

Run
===
From the project root:
```
java -jar build/libs/indent.jar < src/test/resources/test1.bas
```
And the output is shown in the command line

Tests
=====

Tests are in Junit. You can run them with
```
./gradlew test
```