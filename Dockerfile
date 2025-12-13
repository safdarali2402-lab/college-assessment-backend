# Java 17 (Spring Boot 3 compatible)
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Maven wrapper & config
COPY mvnw .
RUN chmod +x mvnw
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build jar
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run application
CMD ["java", "-jar", "target/college-assessment-0.0.1-SNAPSHOT.jar"]
