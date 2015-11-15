# springboot-docker
SpringBoot application using docker for integration tests, to allow parallel builds when using CI servers

The idea is to have an easy and fast way of run three types of tests while developing locally your project with maven:
* **Unit tests**: we will run them frecuently and should be [FIRST](https://pragprog.com/magazines/2012-01/unit-tests-are-first)
* **Integration tests**: should check that all the dependencies work together smothly (database, application context, etc...). We want to run them once in a while
* **Functional tests** (or end to end tests): here we want to treat the whole application as a black box and run some scenarios. Ideally we will start the application and execute tests independently from it.

(All maven commands should be run from the parent folder `springboot-docker-parent`)

##Unit tests
Using `maven-surefire-plugin` we can exclude the folders for integration and functional tests when invoking `mvn test`:

```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven.surefire.version}</version>
                <configuration>
                    <excludes>
                        <exclude>**/integration/**</exclude>
                        <exclude>**/functional/**</exclude>
                    </excludes>
                </configuration>
            </plugin>
```
This should be quite fast to run

##Integration test
The plugin `maven-failsafe-plugin`, assiciated with the phase `verify`, will run the tests included in the folder `**/integration/**`. It can use also the phases `pre-integration-test` and `post-integration-test` for setup/teardown other systems like databases. As this uses the same configuration than the functional tests, a different `profile` for each one needed to be created. 
Run them with `mvn -Pintegration-tests verify` (this will also run the unit tests too).

##Functional tests
Now we would like to build the web service into an auto-executable fat jar, put it in a docker container, build it and run it. And once that is done, run some tests agains that container.
Again, `maven-failsafe-plugin` along with `exec-maven-plugin` will help us here with the phase `pre-integration-test` for buid the docker image and run it, and `post-integration-test` for stop and remove it.
