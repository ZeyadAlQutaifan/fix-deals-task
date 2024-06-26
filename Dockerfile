# Start with a base image containing Maven and JDK 17
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project descriptor and download dependencies
COPY pom.xml .
COPY app .
COPY common .
COPY controller .
COPY repository .
COPY service .
COPY validation .
RUN mvn -B dependency:go-offline
RUN mvn -B package -DskipTests

# Create the final Docker image
FROM openjdk:17-jdk-alpine

# Set environment variables for the application
ENV SERVER_PORT=8081
ENV DATASOURCE_URL=jdbc:postgresql://localhost:5432/fxdeals
ENV DATASOURCE_USERNAME=sa
ENV DATASOURCE_PASSWORD=P@ssw0rd

# Set the working directory
WORKDIR /app

# Copy the JAR file from the Maven build stage
COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port your application runs on (default Spring Boot port)
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "./app.jar"]
