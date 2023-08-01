FROM maven:3.9.3 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn clean package

FROM openjdk:17
COPY --from=builder /app/target/spring-redis-app-1.0.jar /app/spring-redis-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "spring-redis-app.jar"]