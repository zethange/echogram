FROM ghcr.io/graalvm/jdk-community:23

WORKDIR /app

#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY entrypoint.sh .

ENTRYPOINT ["sh", "/app/entrypoint.sh"]