FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/transactions-0.0.1-SNAPSHOT.jar /app/

# Expose the port the application runs on which is optional as we can 
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/transactions-0.0.1-SNAPSHOT.jar"]
