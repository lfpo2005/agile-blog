FROM openjdk:11-jdk-slim

COPY ${JAR_FILE} app.jar
ARG JAR_FILE=target/*.jar

ENV PORT 8080
ENV SPRING_DATASOURCE_USERNAME postgres
ENV SPRING_DATASOURCE_PASSWORD postgres
ENV SPRING_DATASOURCE_URL jdbc:postgresql://spring_db:5432/agileblog

CMD ["java", "-jar", "/app.jar"]
