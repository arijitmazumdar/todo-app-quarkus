# todo-app-quarkus
To build a a simple UI based app using Quarkus, JQuery and Postgresql

Pre-requisite
=============
The application is supposed to run as a maven project inside a graal vm minimal docker with access to postgresql, which has been assumed to be running as a docker as well.
1. Java 1.8+ installed
2. Any IDE
3. Maven
4. Docker
5. Graal VM **(1.6)**
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

Working on the development cycle
=============================
* Run postgresql as a docker image
```docker run -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -d -p 5432:5432 postgres:9.4 ```
* Compile the code using 
```./mvnw compile```
* Test
```./mvnw test```
* Run in the development mode
```./mvnw compile quarkus:dev```
Run the project as standalone docker mode
=========================================
* To build a native binary
```
mvn clean install -DskipTests -DskipITs
./mvnw package -Pnative
```
* To build docker image use the following command. I have pushed the image into my docker repo, please change appropriately
```
./mvnw package -Pnative -Dnative-image.container-runtime=docker
docker build -f src/main/docker/Dockerfile.native -t arijitmazumdar/todo-app-quarkus .
```
* To run everything together, I have ran postgre docker and application docker in the same docker network. And then send the new DB URL as environment variable to the app docker. The script is located under `src/scripts` directory. Running the script is super easy as mentioned below. **If the script breaks in between one need to delete docker resources manually. The script will break if ran after successfully.**
```
bash -x ./src/scripts/deployDocker.sh
```




