FROM openjdk:11.0.15-jdk
EXPOSE 8080
ADD target/spring-security-with-ducku.jar spring-security-with-ducku.jar
ENTRYPOINT ["java", "-jar", "/spring-security-with-ducku.jar"]
