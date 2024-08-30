# 1. 빌드 단계
FROM gradle:8.9.0-jdk17 AS builder
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

RUN pwd
# 2. 패키지 단계
FROM amazoncorretto:17

COPY --from=builder /home/gradle/build/libs/*.jar /app.jar

EXPOSE 8080



CMD ["java", "-jar", "/app.jar"]
