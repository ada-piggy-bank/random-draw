FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ARG PROPERTIES_FILE=target/*application.properties
COPY ${JAR_FILE} app.jar
COPY ${PROPERTIES_FILE} application.properties
ENTRYPOINT ["java","-jar","/app.jar"]