# MyFreezer

Inventory Management System

## Setup
Need Docker and Java 8 to be installed on your system just go into the root folder and run the command below

All-in-one clean and restart from scratch:
```
docker-compose down -v && ./gradlew clean build && docker-compose up -d --build
```