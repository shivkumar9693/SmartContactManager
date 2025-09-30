# Step 1: Build the app using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the built JAR with JDK
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/SmartContactManager-*.jar app.jar

# Expose port (must match server.port in application.properties)
EXPOSE 8282

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
