# Backend Modulith

Multi-module Gradle project for Java components powering simonrowe.dev.

## Architecture

This is a modular monolith containing the following modules:

### Modules

- **`modules/model`** - Common data models and DTOs
- **`modules/component-test`** - Test utilities and shared test components
- **`modules/api-gateway`** - REST API gateway service
- **`modules/search-service`** - Elasticsearch-based search service

## Technology Stack

- **Java 21** - Primary language
- **Spring Boot 3.3.5** - Application framework
- **Gradle** - Build tool (Groovy DSL)
- **Spring WebFlux** - Reactive web framework
- **Elasticsearch** - Search engine
- **Apache Kafka** - Event streaming
- **PostgreSQL** - Primary database
- **Redis** - Caching
- **MongoDB** - Document storage

## Application Flows

### REST API Flows

#### 1. Contact Us Flow
```
POST /api/contact
├── ContactUsController (api-gateway)
├── ContactUseCase (api-gateway)
├── EmailSender (api-gateway)
└── SendGrid Email Service
```

#### 2. Resume Generation Flow
```
POST /api/resume
├── ResumeController (api-gateway)
├── ResumeUseCase (api-gateway)
├── CmsResumeRepository (api-gateway)
├── CMS API Call
└── PDF Generation
```

#### 3. File Upload Flow
```
POST /api/upload
├── UploadController (api-gateway)
├── CompressFileUseCase (api-gateway)
└── File Processing
```

#### 4. Blog Search Flow
```
GET /api/search/blogs?q={query}
├── BlogController (search-service)
├── SearchBlogsUseCase (search-service)
├── BlogIndexRepository (search-service)
└── Elasticsearch Query
```

#### 5. Site Search Flow
```
GET /api/search/site?q={query}
├── SiteController (search-service)
├── SearchSiteUseCase (search-service)
├── SiteSearchRepository (search-service)
└── Elasticsearch Query
```

### Kafka Event Flows

#### 1. CMS Content Update Flow
```
CMS Webhook Event
├── WebhookController (api-gateway)
├── Kafka Producer
├── cms-events Topic
├── KafkaEventConsumer (search-service)
├── IndexBlogUseCase (search-service)
└── Elasticsearch Index Update
```

#### 2. Site Content Indexing Flow
```
CMS Content Change
├── cms-events Topic
├── KafkaEventConsumer (search-service)
├── IndexSiteUseCase (search-service)
└── Elasticsearch Index Update
```

#### 3. Scheduled Synchronization Flow
```
@Scheduled CmsSynchronization (search-service)
├── CmsRestApi (search-service)
├── Fetch All Content
├── BlogMapper/JobMapper/SkillsGroupMapper
└── Bulk Elasticsearch Update
```

## Event Types

### Kafka Topics
- **`cms-events`** - CMS content change notifications

### Event Schemas
```json
{
  "eventType": "BLOG_UPDATED|BLOG_DELETED|JOB_UPDATED|SKILLS_UPDATED",
  "entityId": "string",
  "timestamp": "ISO-8601",
  "data": {}
}
```

## Building and Running

### Prerequisites
- Java 21
- Docker (for containerized services)

### Build Commands
```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :modules:api-gateway:build

# Run tests
./gradlew test

# Create Docker images
./gradlew :modules:api-gateway:bootBuildImage
./gradlew :modules:search-service:bootBuildImage
```

### Running Services
```bash
# Start api-gateway
./gradlew :modules:api-gateway:bootRun

# Start search-service
./gradlew :modules:search-service:bootRun
```

## Configuration

### Environment Variables
- `DOCKER_USERNAME` - Docker registry username
- `DOCKER_PASSWORD` - Docker registry password
- `GITHUB_ACTOR` - GitHub actor for package access
- `GITHUB_TOKEN` - GitHub token for package access

### Application Properties
Each service has its own `application.yml` in `src/main/resources`.

## Dependencies

### Inter-module Dependencies
- `api-gateway` → `model`, `component-test`
- `search-service` → `model`, `component-test`

### External Dependencies
- Spring Boot 3.3.5
- Spring Cloud 2023.0.3
- Elasticsearch
- Kafka
- SendGrid (email)
- PDF generation libraries

## Testing

The project uses:
- **JUnit 5** - Test framework
- **Mockito** - Mocking framework
- **Testcontainers** - Integration testing
- **AssertJ** - Assertions

Test utilities are provided in the `component-test` module for:
- PostgreSQL testing
- MongoDB testing
- Elasticsearch testing
- Kafka testing
- Redis testing
- Vault testing
- JWT utilities
- Wiremock testing

## Monitoring

- **Spring Actuator** - Health checks and metrics
- **Micrometer Tracing** - Distributed tracing
- **Zipkin** - Trace visualization