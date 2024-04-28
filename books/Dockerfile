# Use a JDK base image to have the necessary tools for building the project
FROM eclipse-temurin:21-jdk-alpine as build

# Set a working directory
WORKDIR /app

# Optionally, copy the Gradle wrapper and other gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the rest of your project
COPY src src

# Execute the build, which produces the jar file
RUN ./gradlew build

# Now, create the final image
FROM eclipse-temurin:21-jdk-alpine

# Create a volume for temporary files
VOLUME /tmp

# Copy the jar from the build stage to the final image
COPY --from=build /app/build/libs/*.jar app.jar

# Set the entry point to run the application
ENTRYPOINT ["java","-jar","/app.jar"]
