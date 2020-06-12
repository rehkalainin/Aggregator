

FROM openjdk:8-jre-alpine
EXPOSE 8080
ADD target/scala-2.13/agregator_2.13-0.1.jar app.jar
RUN ["scala","-jar","/app.jar"]

