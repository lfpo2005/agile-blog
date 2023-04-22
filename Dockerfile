FROM openjdk:11-jdk-slim

COPY target/app.jar /app.jar

ENV PORT 8080
ENV SPRING_DATASOURCE_USERNAME postgres
ENV SPRING_DATASOURCE_PASSWORD postgres
ENV SPRING_DATASOURCE_URL jdbc:postgresql://spring_db:5432/agileblog

CMD ["java", "-jar", "/app.jar"]
