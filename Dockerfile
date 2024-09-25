# Usa una imagen base de OpenJDK 17 para ejecutar aplicaciones Java
FROM openjdk:17-jdk-slim

# Añade un argumento para la versión del archivo JAR, basado en el POM
ARG JAR_FILE=target/gestion-inventario-0.0.1-SNAPSHOT.jar

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR desde el directorio target en tu máquina host al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto 8080, que es el puerto por defecto para Spring Boot
EXPOSE 8080

# Comando para ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
