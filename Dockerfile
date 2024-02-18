FROM openjdk:21

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} school-management-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/school-management-app.jar"]