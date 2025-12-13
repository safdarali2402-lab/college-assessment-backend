# Java 17 (Spring Boot 3 compatible)
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Maven wrapper & config
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cache layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build jar
RUN ./mvnw clean package -DskipTests

# Expose port (Render provides PORT env)
EXPOSE 8080

# Run application
CMD ["java", "-jar", "target/college-assessment-0.0.1-SNAPSHOT.jar"]
