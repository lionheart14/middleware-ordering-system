# Schritt 1: Build-Umgebung mit Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Kopiere die pom.xml und die .proto Datei (aus dem api-Ordner eins drüber)
COPY pom.xml .
COPY ../api ../api

# Lade Abhängigkeiten und kompiliere den Code (generiert auch die gRPC Klassen)
COPY src ./src
RUN mvn clean package -DskipTests

# Schritt 2: Laufzeit-Umgebung (schlank)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/server-java-1.0-SNAPSHOT.jar app.jar

# Port für gRPC
EXPOSE 50051
ENTRYPOINT ["java", "-jar", "app.jar"]