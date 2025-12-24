# Use official OpenJDK runtime as base image
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Copy JAR file (update name to match your actual JAR)
COPY target/smart-payment-tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will use $PORT env var)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]