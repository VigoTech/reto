# VigoJug "Reto 17" solution by @antonmry

Simple solution using Groovy and some interesting features of the language:

- Spock Framework for unit testing and how to check the input and output from console.
- Grab to retrieve libraries without use complex dependency systems as Gradle or Maven.
- @StaticCompile to have very similar perfomance to Java
- Usage of `int * String` to `multiply` an string. 

My goal were (in order of priority):

* Develop something easy to read and maintain.
* Performance.
* As much less code as possible.

## How to execute it

Install groovy if it's already installed: 

```bash
curl -s "https://get.sdkman.io" | bash
sdk install groovy
```

Execute it:

```bash
groovy BasicFormatter.groovy
```

Execute tests:

```bash
groovy BasicFormatterSpec.groovy
```

**Note**: if you want to execute test from IntelliJ Idea, it's better to use the `Grab` lines to get the libraries (alt+enter) and after that, comment them (IntelliJ doesn't manage well use scripts to test) 
