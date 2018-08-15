# Spring Boot REST API

This is example of REST API built with Java 8, Maven, Spring Boot (2.0.3.RELEASE), Spring, H2 database.


## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/ibarovic/MeterReadingRestWS.git
```

**2. Set logging directory** 

- open `src/main/resources/logback.xml`
- change `ROOT_DIR` to a path where you want the logs to be saved

**3. Build and run the app using maven**

```bash
mvn clean package
java -jar target/MeterReadingRestWS-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

### H2 in-memory database connection

- http://localhost:8080/h2-console/login.jsp
  - JDBC URL: jdbc:h2:mem:testdb
  - User Name: Sa
  - Password: (blank password)
  - schema location: src/main/resources/schema.sql
  
## Explore Rest APIs

The app defines following CRUD APIs.

    GET /ratios
    GET /ratios?profile=:profile
    GET /ratios?profile=:profile&month=:month
    POST /ratios
    DELETE /ratios/{profile}
    
    GET /meter-readings
    GET /meter-readings?meterId=:meterId
    GET /meter-readings?meterId=:meterId&profile=:profile
    GET /meter-readings?meterId=:meterId&profile=:profile&month=:month
    POST /meter-readings
    DELETE /meter-readings/{meterId}
    DELETE /meter-readings/{meterId}/{profile}

You can test them using postman or any other rest client.
