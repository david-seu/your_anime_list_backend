# Use the official Gradle image with OpenJDK 21 to build the project
FROM gradle:8.2.1-jdk21 AS build

# Copy the project files into the container
COPY . .

# Build the project and skip tests
RUN gradle clean build -x test

# Use a minimal JDK 21 image to run the application
FROM openjdk:21-slim

# Copy the built jar file from the build stage to the final stage
COPY --from=build /home/gradle/build/libs/your_anime_list_backend-0.0.1-SNAPSHOT.jar demo.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java","-jar","demo.jar"]