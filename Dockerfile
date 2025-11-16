# Stage 1: Build the application
FROM maven:3.9.6-amazoncorretto-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM amazoncorretto:17-alpine
WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables (can also override in docker-compose)
ENV SPRING_PROFILES_ACTIVE=prod \
    SERVER_PORT=8080

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
