# todo-app-quarkus
To build a a simple UI based app using Quarkus, JQuery and Postgresql

Pre-requisite
=============
The application is supposed to run as a maven project inside a graal vm minimal docker with access to postgresql, which has been assumed to be running as a docker as well.
1. Java 1.8+ installed
2. Any IDE
3. Maven
4. Docker
5. Graal VM
6. Any kubernetes environment (I have used k3s.io)

To create the scaffolding
=========================
The following command has been used to build the scaffolding artifacts. 
```
mvn io.quarkus:quarkus-maven-plugin:0.13.1:create \
     -DprojectGroupId=org.todo \
     -DprojectArtifactId=todoapp \
     -DclassName="org.todo.ToDoService" \
     -Dpath="/todos" \
     -Dextensions="quarkus-hibernate-orm-panache,quarkus-jdbc-postgresql,quarkus-resteasy-jsonb"
```
Run as standalone docker mode
=============================
One can compile the code using 
`./mvnw compile`
Test
`./mvnw test`
Run in the development mode
`./mvnw compile quarkus:dev`
To run postgresql as a docker image
`docker run -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -d -p 5432:5432 postgres:9.4 `


