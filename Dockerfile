FROM maven:3.8.4-openjdk-17 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/communitycenter-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

# Comando para rodar o aplicativo
CMD [ "java", "-jar", "app.jar" ]
