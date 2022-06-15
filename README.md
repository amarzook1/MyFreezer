# MyFreezer

Inventory Management System with good Integration and Unit testing.

## Setup
### Build application
You may need Java 8 installed on your system to run
To build the application:
Unix:
```
./gradlew build
```
Windows:
```
.\gradlew.bat build
```
This generates the deployable `MyFreezer-0.0.1-SNAPSHOT.jar` in `build/libs`.

## Docker
Consists of two containers:
* JDK 8 - `storage-application` , running on port 8085
* PostgreSQL 14 - `postgres14.2-db-myfreezer`, running on port 5434

### Building
After building the application, build the containers:
```
docker-compose build
```

Bring them up:
```
docker-compose up -d
```

### Stopping
To remove containers:
```
docker-compose down
```

To remove containers and all data:
```
docker-compose down -v
```
### Other useful commands
You may need Java 8 installed on your system to run
All-in-one clean and restart from scratch:
```
docker-compose down -v && ./gradlew clean build && docker-compose up -d --build
```

headers:
Key - API_TOKEN
Value - pa55word
