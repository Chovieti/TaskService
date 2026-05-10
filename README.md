# Task Service

Сервис для управления задачами. Позволяет создавать задачи, назначать пользователям, изменять статусы. Генерирует события в Kafka при каждом изменении задачи.

## Технологии

- Java 17+
- Spring Boot 3
- Spring Data JPA (Hibernate)
- PostgreSQL
- Apache Kafka
- Docker / Docker Compose

## Функциональность

- **Пользователи**: создание, получение по ID.
- **Задачи**: создание, получение по ID, пагинированный список, назначение исполнителя, изменение статуса.
- **События**: при создании, назначении или изменении статуса задачи отправляется событие `TaskEvent` в Kafka topic `task-events`.

## Запуск через Docker Compose

Сервис полностью контейнеризирован. Для запуска всех зависимостей (PostgreSQL, Zookeeper, Kafka, Kafka UI) и самого приложения:

Выполните команду:

```bash
docker-compose up -d
```

После успешного запуска:

- Приложение будет доступно по адресу: `http://localhost:8080`
- Kafka UI: `http://localhost:8085`
- PostgreSQL: `localhost:5432` (база `tasks_db`, пользователь `postgres`)

## API Эндпоинты

### Пользователи

#### Создать пользователя
```
POST /users
Content-Type: application/json

{
  "name": "Иван Петров",
  "email": "ivan@example.com"
}
```
**Ответ:** `201 Created` с UUID пользователя в теле.

#### Получить пользователя
```
GET /users/{userId}
```
**Ответ:** `200 OK`
```json
{
  "name": "Иван Петров",
  "email": "ivan@example.com"
}
```

### Задачи

#### Создать задачу
```
POST /tasks
Content-Type: application/json

{
  "title": "Сделать отчёт",
  "description": "Подготовить квартальный отчёт"
}
```
**Ответ:** `201 Created` с UUID задачи.

#### Получить задачу по ID
```
GET /tasks/{id}
```
**Ответ:** `200 OK`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Сделать отчёт",
  "description": "Подготовить квартальный отчёт",
  "status": "CREATED",
  "assigneeId": null
}
```

#### Пагинированный список задач
```
GET /tasks?page=0&size=10
```
Параметры: `page` (по умолчанию 0, ≥0), `size` (от 1 до 50, по умолчанию 10).  
**Ответ:** `200 OK` c `Page<TaskResponse>`.

#### Назначить исполнителя
```
PATCH /tasks/{taskId}/assign
Content-Type: application/json

{
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```
**Ответ:** `200 OK` с обновлённой задачей.

#### Изменить статус задачи
```
PATCH /tasks/{id}/status
Content-Type: application/json

{
  "status": "IN_PROGRESS"   // или CREATED, DONE
}
```
**Ответ:** `200 OK` с обновлённой задачей.

## События Kafka

При каждом изменении задачи в topic `task-events` отправляется JSON:

```json
{
  "eventType": "TASK_CREATED" | "TASK_ASSIGNED" | "TASK_STATUS_CHANGED",
  "taskId": "...",
  "title": "...",
  "description": "...",
  "status": "CREATED" | "IN_PROGRESS" | "DONE",
  "assigneeId": "..." | null
}
```

Просмотреть события можно через Kafka UI (порт `8085`) или подключив отдельного консюмера.

## Структура проекта

```
src/
├── main/
│   ├── java/com/example/task_service/
│   │   ├── controller/       # REST контроллеры
│   │   ├── service/          # Бизнес-логика и продюсер Kafka
│   │   ├── repository/       # Spring Data JPA репозитории
│   │   ├── model/            # Entity и перечисления
│   │   ├── dto/              # Request/Response DTO
│   │   ├── event/            # Объект события TaskEvent
│   │   ├── exception/        # Кастомные исключения
│   │   ├── kafka/            # Константы топиков
│   │   └── mapper/           # Мапперы сущностей
│   └── resources/
│       └── application.properties
├── docker-compose.yml
└── Dockerfile
```

## Примечания

- Статусы задач: `CREATED`, `IN_PROGRESS`, `DONE`.
- При попытке назначить несуществующего пользователя или несуществующую задачу возвращается `404 Not Found` с соответствующим сообщением.
- Валидация входных данных: `@NotNull`, `@NotBlank`, `@Min`/`@Max`.
- Глобальный обработчик исключений возвращает единообразные ошибки с полями `error`, `message`, `timestamp`.
- При запуске через Docker Compose используется образ `postgres:16` и `confluentinc/cp-kafka:7.5.0`.