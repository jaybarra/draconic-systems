FROM eclipse-temurin:17

RUN mkdir -p /opt/draconic-systems
WORKDIR /opt/draconic-systems

COPY ./target/ds-server-standalone.jar .

EXPOSE 3000

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ds-server-standalone.jar"]

HEALTHCHECK CMD curl -f http://localhost:3000 || exit 1
