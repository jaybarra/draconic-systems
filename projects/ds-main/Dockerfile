FROM eclipse-temurin:17

RUN mkdir -p /opt/draconic-systems
WORKDIR /opt/draconic-systems

COPY ./target/ds-server-standalone.jar .

EXPOSE 3000

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ds-server-standalone.jar"]

HEALTHCHECK --interval=30s --retries=5\
    CMD ["java", "-classpath", "ds-server-standalone.jar", "ds.util.healthcheck"]
