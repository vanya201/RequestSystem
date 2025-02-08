ARG JAVA_VERSION
FROM openjdk:${JAVA_VERSION}-jdk-slim
WORKDIR /app
COPY out/my-app-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]