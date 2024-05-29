# Используем базовый образ с JDK
FROM amazoncorretto:17-alpine-jdk

# Установка Nginx и Supervisor
RUN apk update && apk add --no-cache nginx supervisor

# Копируем наш JAR файл
COPY target/*.jar /app.jar

# Копируем конфигурационные файлы Nginx и Supervisor
COPY nginx.conf /etc/nginx/nginx.conf
COPY supervisord.conf /etc/supervisord.conf

# Открываем порты
EXPOSE 80 8080

# Запускаем Supervisor для управления процессами
CMD ["supervisord", "-c", "/etc/supervisord.conf"]
