# springboot-docker
SpringBoot application using docker for integration tests, to allow parallel builds when using CI servers

The idea is to have an easy and fast way of run three types of tests while developing locally your project with maven:
* **Unit tests**: we will run them frecuently and should be [FIRST](https://pragprog.com/magazines/2012-01/unit-tests-are-first)
* **Integration tests**: should check that all the dependencies work together smothly (database, application context, etc...). We want to run them once in a while
* **Functional tests** (or end to end tests): here we want to treat the whole application as a black box and run some scenarios. Ideally we will start the application and execute tests independently from the application.
