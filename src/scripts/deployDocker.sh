#!/bin/bash
PASSWORD=postgres
USERNAME=postgres
DB=postgres
NETWORK=todo-app-network

docker network create ${NETWORK}


pg_container_id=$(docker run --name backend --network ${NETWORK}  \
-e POSTGRES_PASSWORD=${PASSWORD} -e POSTGRES_DB=${DB}  \
-e POSTGRES_USER=${USERNAME} -d -p 5432:5432 postgres:9.4)    


pg_ip=$(docker inspect --format "{{(index .Containers \"${pg_container_id}\").IPv4Address}}" ${NETWORK} | cut -d "/" -f 1)

echo "sleeping for 5 sec to postgres to warm up" && sleep 5
echo "Backend is running at ${pg_ip}"

url=jdbc:postgresql://${pg_ip}/${DB}

docker run --name frontend --network  ${NETWORK} -e QUARKUS_DATASOURCE_URL=${url} \
-e QUARKUS_DATASOURCE_USERNAME=${USERNAME} \
-e QUARKUS_DATASOURCE_PASSWORD=${PASSWORD} \
-d -p8080:8080 arijitmazumdar/todo-app-quarkus

echo "curl http://localhost:8080/todos" && curl "http://localhost:8080/todos"
