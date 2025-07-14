FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY task-manager-api-spec ./task-manager-api-spec
COPY task-manager-api-impl ./task-manager-api-impl
RUN mvn clean install

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/task-manager-api-impl/target/*.jar task-manager.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "task-manager.jar"]