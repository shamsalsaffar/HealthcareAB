# Steg 1: Använd OpenJDK 26 (EA 29) som bas-image för byggprocessen
# Om OpenJDK 26 inte finns tillgängligt, kan du byta till en annan version som OpenJDK 17 eller en stabil version av OpenJDK 26 när den finns.
FROM maven:3.8.4-openjdk-17 AS build

# Steg 2: Ange arbetskatalogen
WORKDIR /app

# Steg 3: Kopiera Maven-pom.xml och source-kod
COPY pom.xml .
COPY src /app/src

# Steg 4: Bygg applikationen med Maven (skapar en JAR-fil i target/)
RUN mvn clean install -DskipTests

# Steg 7: Kopiera den byggda JAR-filen från byggcontainern
COPY ./target/health-care-app.jar /app/health-care-app.jar

# Steg 8: Kör applikationen med Java
CMD ["java", "-jar", "health-care-app.jar"]
