# Use an official Maven image to build the application
FROM maven:3.6.3-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copies into the working directory
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an OpenJDK image to run the Spring Boot application
FROM azul/zulu-openjdk:17-latest

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the Maven build container
COPY --from=build /app/target/*.jar /app/laris-assistant.jar

# Expose the port on which the application will run
EXPOSE 8080

# Define the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/laris-assistant.jar"]