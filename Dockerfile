# Estágio de build (compilação)
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

# Estágio final (imagem leve)
FROM eclipse-temurin:21-jdk-alpine

# Instala certificados SSL e dependências necessárias
RUN apk add --no-cache ca-certificates openssl

WORKDIR /app

# Copia o JAR gerado no estágio de build
COPY --from=build /app/target/biblioteca-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]