# Fintech Microservice - Authentication & Notification Service

A robust, enterprise-grade Spring Boot microservice implementing Clean Architecture (Domain-Driven Design), Outbox Pattern, Event-Driven Architecture with Apache Kafka, Redis Distributed Caching/Rate Limiting, and PostgreSQL.

## 🏗 System Architecture

The project is structured according to **Clean Architecture / Hexagonal Architecture / DDD**:

- **`domain`**: Core business entities, value objects, domain events, domain service interfaces, and business rules (zero external dependencies).
- **`application`**: Use cases, command & query handlers, orchestrating business logic and transactions.
- **`infrastructure`**: External adapters, PostgreSQL JPA repositories, Kafka producers/consumers, Redis adapters, Mail/AWS SNS providers, Outbox Relay schedulers, and Security configurations.
- **`controller`**: REST API endpoints, DTO request/response schemas, validation, and HTTP presentation layer.
- **`start`**: Spring Boot application bootstrap module, Flyway DB migrations, email templates, and main configuration.

## 🚀 Tech Stack

- **Java 25** & **Spring Boot 4.0.x**
- **Database**: PostgreSQL with Flyway Migrations
- **Messaging / Event Streaming**: Apache Kafka (KRaft mode cluster)
- **Cache & Rate Limiting**: Redis & Lua scripts
- **Security**: Spring Security & JJWT (Access & Refresh tokens)
- **Email & Notification**: JavaMailSender & AWS SNS
- **Object Mapping**: MapStruct & Lombok

## 🛠 Local Setup & Development

### 1. Prerequisites
- JDK 25 installed
- Docker & Docker Compose
- Maven 3.9+

### 2. Start Infrastructure Services
Start PostgreSQL, Redis, Kafka 3-node cluster, and Kafka UI:
```bash
docker compose up -d
```
- PostgreSQL: `localhost:5434`
- Redis: `localhost:6379`
- Kafka Brokers: `localhost:9092, localhost:9093, localhost:9094`
- Kafka UI: `http://localhost:8089`

### 3. Environment Variables Configuration
Copy `.env.example` to `.env` or set environment variables:
```bash
cp .env.example .env
```
Fill in your local credentials (DB password, mail SMTP credentials, AWS SNS credentials if needed).

### 4. Build and Run
```bash
mvn clean install
cd start
mvn spring-boot:run
```
The API server will run at `http://localhost:8090/api`.

## 🔒 Security Best Practices
- Sensitive credentials (passwords, JWT secrets, cloud keys) are strictly injected via environment variables.
- Sensitive files (`.env`, `application-local.yaml`, `target/`, IDE workspace caches) are excluded via `.gitignore`.
