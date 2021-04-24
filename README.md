# PagoPAExercises

Exercises based on PagoPA questions. In particular the following questions have been asked:

1. Write some code that will flatten an array of arbitrarily nested arrays of integers into a flat array of integers. e.g. [[1,2,[3]],4] -> [1,2,3,4]. (provide a link to your solution)

2. Write a very simple chat server that should listen on TCP port 10000 for clients. The chat protocol is very simple, clients connect with "telnet" and write single lines of text. On each new line of text, the server will broadcast that line to all other connected clients. Your program should be fully tested too. (provide a link to your solution)


## How to use this project
### Package stucture
- Code related to Question n°1 (flat array) in the package `$it.canofari.pagopaexcercises.flatarray`.
- Code related to Question n°2 (simple chat server) in the package `$it.canofari.pagopaexcercises.chatserver`. In particular in for this exercise in the **test** folder in addition to the related Test class there is a ChatClient which has been used for testing purpose

### Build Project
Compile the project (by execuring related tests) by executing the following command:

```sh
mvn clean install
```
> Note: currently the project pom.xml requires jdk 11.