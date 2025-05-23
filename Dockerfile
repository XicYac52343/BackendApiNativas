# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN chmod +x mvnw && ./mvnw -B -DskipTests clean package

# Etapa de ejecución
FROM amazoncorretto:21-alpine-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
