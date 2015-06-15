FROM java:8
ADD zuul.jar app.jar
EXPOSE 8765
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]