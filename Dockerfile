FROM openjdk:16-jdk-alpine

RUN mkdir -p /app
WORKDIR /app

COPY ./target/uberjar/ds-server.jar .

EXPOSE 8080

CMD ["java" "-jar" "ds-server.jar"]
