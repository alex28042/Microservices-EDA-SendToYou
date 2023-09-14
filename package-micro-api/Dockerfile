FROM maven:3.9-amazoncorretto-20 AS builder
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

FROM openjdk:20
COPY --from=builder target/package-micro-api-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-Dspring.profiles.active=local", "-jar", "/app.jar"]