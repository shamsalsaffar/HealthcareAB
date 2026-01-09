# ========= Build stage =========
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copy build files
COPY pom.xml .
COPY src ./src
COPY checkstyle.xml ./checkstyle.xml

# Build jar
RUN mvn clean package -DskipTests

# ========= Runtime stage =========
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/health-care-app.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
