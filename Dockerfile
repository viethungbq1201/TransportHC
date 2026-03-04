# Stage 1: Build với Maven và Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng với JRE 21 nhẹ
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Lưu ý: COPY đúng từ thư mục /app/target của stage build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
