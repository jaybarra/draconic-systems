FROM eclipse-temurin:17

RUN mkdir -p /opt/draconic-systems
WORKDIR /opt/draconic-systems

COPY ./target/ds-server-standalone.jar .

EXPOSE 3000

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ds-server-standalone.jar"]

HEALTHCHECK --interval=30s --retries=3 --start-period=10s \
  CMD curl --fail http://localhost:3000/api/healthcheck || exit 1
# TODO restore custom checker CMD ["java", "-classpath", "ds-server.jar", "ds.util.healthcheck"]
