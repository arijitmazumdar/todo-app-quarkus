# todo-app-quarkus
This is a small project to build a simple browser based app using Quarkus, JQuery and Postgresql. If you are able to run application, the application can be accessed at http://localhost:8080/.  The UI component are located in `src/main/resources/META-INF/`. The focus was more on Quarkus side, others were complementary. I was interested to get an experience on Quarkus and Graal VM. I was anxious to check the native image has really made booting faster and I must agree that I am not disappointed. But the ride was not entirely smooth, mostly of my lack of knowledge (No doubt on that) and technology is yet to mature. Stackoverflow is not overflowing with information!! I have documented and shared my learning in another blog, can be found here.

Things definitely could have been written better way. Do let know if you think things could have done. Mostly this is for the beginners and lazy developers (like me) to give a quick start on the subject. 


Pre-requisite
=============
The application is supposed to run as a maven project inside a graal vm minimal docker with access to postgresql, which has been assumed to be running as a docker as well.
1. Java 1.8+ installed and JAVA_HOME is set propoerly.
2. Any IDE
3. Maven
4. Docker
5. Graal VM **(Community Edition 1.0 RC16)** installed and configured 
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
* Run the application in development mode using.
```./mvnw compile quarkus:dev```

Run the project as standalone docker mode
=========================================
* To build a native binary
```
./mvnw package -Pnative # mvn clean install -DskipTests -DskipITs -- if you face Jandex Indexing problem
```
* To build docker image use the following command. I have pushed the image into my docker repo, please change appropriately
```
./mvnw package -Pnative -Dnative-image.container-runtime=docker
docker build -f src/main/docker/Dockerfile.native -t arijitmazumdar/todo-app-quarkus .
docker build -f src/main/docker/Dockerfile.native.distroless -t arijitmazumdar/todo-app-quarkus . #to build a distroless docker
```
* To run everything together, I have ran postgre docker and application docker in the same docker network. And then send the new DB URL as environment variable to the app docker. The script is located under `src/scripts` directory. Running the script is super easy as mentioned below. **If docker network already available it won't create but docker containers will be re-created**
```
bash ./src/scripts/deployDocker.sh #Change the docker image name in the file src/scripts/deployment.env
```

Things yet to be complete is to deploy into a kubernetes, which will be made available shortly.


