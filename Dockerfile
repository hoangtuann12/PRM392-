# Sử dụng một image của OpenJDK để build ứng dụng
FROM openjdk:17-jdk-slim AS build

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file pom.xml và source code vào container
COPY pom.xml /app
COPY src /app/src

# Build ứng dụng Spring Boot với Maven
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Chuyển sang image nhẹ hơn để chạy ứng dụng
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Copy file jar đã build từ bước trước
COPY --from=build /app/target/PRM392-0.0.1-SNAPSHOT.jar /app/app.jar

# Mở cổng mà Spring Boot sẽ chạy
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
