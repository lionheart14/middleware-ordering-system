# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. pom.xml kopieren
COPY server-java/pom.xml .

# 2. Proto-Ordner erstellen und Datei kopieren
RUN mkdir -p src/main/resources/proto
COPY api/ordering.proto src/main/resources/proto/

# 3. Restlichen Source Code kopieren
COPY server-java/src ./src

# 4. Bauen (Jetzt findet Maven die Proto-Datei unter src/main/resources/proto)
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/server-java-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

EXPOSE 50051
ENTRYPOINT ["java", "-jar", "app.jar"]