FROM openjdk:8
ADD build/libs/MyFreezer-0.0.1-SNAPSHOT.jar MyFreezer-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "MyFreezer-0.0.1-SNAPSHOT.jar"]