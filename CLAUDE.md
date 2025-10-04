# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :modules:api-gateway:build
./gradlew :modules:search-service:build
./gradlew :modules:model:build
./gradlew :modules:component-test:build

# Run tests for all modules
./gradlew test

# Run tests for specific module
./gradlew :modules:api-gateway:test
./gradlew :modules:search-service:test

# Run single test class
./gradlew :modules:api-gateway:test --tests com.simonjamesrowe.apigateway.test.ApiGatewayApplicationTests

# Run single test method
./gradlew :modules:api-gateway:test --tests "com.simonjamesrowe.apigateway.test.ApiGatewayApplicationTests.testMethod"

# Clean build
./gradlew clean build

# Build Docker images
./gradlew :modules:api-gateway:bootBuildImage
./gradlew :modules:search-service:bootBuildImage

# Run services locally
./gradlew :modules:api-gateway:bootRun
./gradlew :modules:search-service:bootRun

# Generate test reports
./gradlew jacocoTestReport

# Check code quality with SonarQube
./gradlew sonarqube

# Run Checkstyle code style checks
./gradlew checkstyleMain checkstyleTest

# Run all quality checks
./gradlew check
```

## Architecture Overview

This is a multi-module Gradle project with a modular monolith architecture. All modules are under the `modules/` directory with cross-module dependencies managed through Gradle project references.

### Module Dependency Graph
```
api-gateway ──┬──> model
              └──> component-test (test only)

search-service ──┬──> model
                 └──> component-test (test only)
```

### Clean Architecture Layers

Each service module follows clean architecture:
- **`core/`** - Business logic (use cases, models, repository interfaces)
- **`dataproviders/`** - External integrations (CMS, SendGrid, Elasticsearch)
- **`entrypoints/`** - Entry points (REST controllers, Kafka consumers, scheduled tasks)
- **`config/`** - Spring configuration classes

### Event-Driven Communication

Services communicate asynchronously via Kafka:
- **Producer**: `api-gateway` publishes to `cms-events` topic via `WebhookController`
- **Consumer**: `search-service` consumes from `cms-events` via `KafkaEventConsumer`
- **Event Types**: `BLOG_UPDATED`, `BLOG_DELETED`, `JOB_UPDATED`, `SKILLS_UPDATED`

### Data Flow Patterns

1. **Synchronous REST**: Client → api-gateway → External Services (CMS, SendGrid)
2. **Asynchronous Events**: CMS Webhook → api-gateway → Kafka → search-service → Elasticsearch
3. **Scheduled Sync**: search-service periodically syncs all content from CMS to Elasticsearch

### Module Conversion Notes

This project was migrated from separate Kotlin/Java repositories:
- **api-gateway**: Mixed Java/Kotlin → Pure Java 21
- **search-service**: Pure Kotlin → Java 21 (conversion in progress)
- Test files converted from MockK to Mockito
- Kotlin coroutines replaced with CompletableFuture/Reactor

### Required Environment Variables

For building Docker images and publishing:
- `DOCKER_USERNAME` - Docker Hub username
- `DOCKER_PASSWORD` - Docker Hub password
- `GITHUB_ACTOR` - GitHub username for package access
- `GITHUB_TOKEN` - GitHub token for accessing private packages

### Technology Stack

- **Java 21** with Spring Boot 3.3.5
- **Gradle** with Groovy DSL (not Kotlin DSL)
- **Spring WebFlux** for reactive programming
- **Testcontainers** for integration testing
- **Lombok** for reducing boilerplate