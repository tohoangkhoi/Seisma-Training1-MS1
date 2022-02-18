FROM openjdk:11
ARG JAR_FILE=target/MS1-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080/TCP
ENTRYPOINT ["java","-jar","/app.jar"]