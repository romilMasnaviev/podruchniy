FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar app.jar
EXPOSE 8080