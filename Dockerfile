# =============================================
# 多阶段构建 Dockerfile
# 阶段1: Maven 编译
# 阶段2: JRE 运行（镜像极小）
# =============================================

# --- 构建阶段 ---
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -q

# --- 运行阶段 ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 创建非 root 用户
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 复制构建产物
COPY --from=builder /app/target/*.jar app.jar

# 切换用户
USER appuser

# 云平台通过 PORT 环境变量指定端口，默认 8088
EXPOSE 8088

# 启动（支持 JVM 内存调优）
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]
