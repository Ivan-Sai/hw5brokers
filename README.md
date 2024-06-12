# Brokerks Project

Цей проект використовує Java, Spring Boot, Kafka, Elasticsearch та Docker для створення системи відправки email листів.

## Вимоги

- Docker

## Інструкції зібрання та запуску

1. В файлі .env встановіть значення змінних середовища для підключення до email-серверу:

```
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USER=youruser
EMAIL_PASSWORD=yourpassword
```

2.Зібрати образ контейнеру сервісу:

```bash
docker build -t brokers .
```

3.Зібрати та запустити Docker контейнери:

```bash
docker-compose up --build
```

Ця команда зібере образи Docker для всіх служб, визначених у файлі `docker-compose.yml`, і запустить їх.

## Використання

Після запуску Docker контейнерів, сервіс буде слухати повідомлення від кафки на порті 9092.

## Зупинка та очищення

Щоб зупинити Docker контейнери, виконайте наступну команду:

```bash
docker-compose down
```

Ця команда зупинить всі контейнери, визначені в файлі `docker-compose.yml`, і видалить їх, а також мережі, які вони
використовували.