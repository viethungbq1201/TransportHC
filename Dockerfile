# Stage 1: Build với Maven và Java 17 (ổn định nhất cho Spring Boot hiện tại)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng với JRE 17 nhẹ
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Lưu ý: COPY đúng từ thư mục /app/target của stage build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
