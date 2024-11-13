
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/bookingScheduleQuery-0.0.1-SNAPSHOT.jar session-request-query-service.jar

EXPOSE 9800

ENTRYPOINT ["java", "-jar", "session-request-query-service.jar"]

ENV TZ=Asia/Seoul