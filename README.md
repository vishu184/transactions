# transactions
Transactions Routine Microservices

## Development

To start your application in the dev profile, run:

```
./mvnw spring-boot:run
```

You can change port from application.yml using server.port 
You need to give postgresql connection details in application.yml file
For running integration test and building it is using h2 in mem db

For Testing API you can use Swagger UI
Link : http://localhost:8080/swagger-ui/index.html
Open API 3 Docs : http://localhost:8080/swagger-ui/index.html


## Building for deployment

### Packaging as jar

```
./mvnw clean verify
```

To ensure everything worked, run:

```
java -jar target/*.jar
```

You can also run script for building, testing and running application

For Windows
```
run.bat
```

For Unix
```
run.sh
```
## Testing

To launch application's tests, run:

```
./mvnw verify
```

## Using Docker to simplify development

Build Docker image 

```
docker build -t image-name .
```

To Run Docker Image:

```
docker run -p 8080:8080 image-name
```

You can change docker exposing port and application exposing port 
