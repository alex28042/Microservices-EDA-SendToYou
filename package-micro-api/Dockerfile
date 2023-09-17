FROM maven:3.9-amazoncorretto-20 AS builder
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package -DskipTests

FROM openjdk:20
COPY --from=builder target/package-micro-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3000
CMD ["java","-jar", "/app.jar"]