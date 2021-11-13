FROM openjdk:8-jdk-alpine

RUN addgroup -S gss && adduser -S gss -G gss

USER gss:gss
ARG JAR_FILE=graphql-service-app/build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
