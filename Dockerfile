FROM openjdk:8
ADD build/libs/MyFreezer-0.0.1-SNAPSHOT.jar MyFreezer-0.0.1-SNAPSHOT.jar
EXPOSE 8080
HEALTHCHECK CMD curl --fail http://localhost:8080/ping || exit 1 
ENTRYPOINT ["java", "-jar", "MyFreezer-0.0.1-SNAPSHOT.jar"]