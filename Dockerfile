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
COPY --from=builder /app/target/ShimmerShine-0.0.1*.jar app.jar

# Set environment variables (can also override in docker-compose)
ENV SPRING_PROFILES_ACTIVE=prod \
    SERVER_PORT=8080

# Expose port
EXPOSE 8080

# Create and use a non-privileged user for better security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
# Run the app
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
