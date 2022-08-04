FROM amazoncorretto:11-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-server", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]
EXPOSE 5001