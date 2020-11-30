FROM openjdk:14

RUN mkdir -p /app
WORKDIR /app

COPY ./target/uberjar/ds-server.jar .

EXPOSE 3000

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ds-server.jar"]

HEALTHCHECK --interval=60s --timeout=30s \
  CMD ["java", "-classpath", "ds-server.jar", "ds.util.healthcheck"]
