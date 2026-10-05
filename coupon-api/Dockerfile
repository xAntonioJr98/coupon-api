# Usa a imagem oficial do Java 21 baseada no Alpine (mais leve)
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o .jar gerado pelo build do Maven para dentro do container
COPY target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando que executa a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]