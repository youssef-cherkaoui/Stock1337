FROM openjdk:17
VOLUME /tmp
ADD target/*.jar Stock1337.jar
ENTRYPOINT ["java", "-jar", "Stock1337.jar"]