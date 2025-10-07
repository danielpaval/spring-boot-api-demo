# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Development Commands

### Building and Running
```bash
# Build the project
.\gradlew build

# Run the application
.\gradlew bootRun

# Run with specific profile
.\gradlew bootRun --args='--spring.profiles.active=dev'
```

### Testing
```bash
# Run all tests
.\gradlew test

# Run specific test class
.\gradlew test --tests "UserServiceTest"

# Run tests with Testcontainers
.\gradlew test -Dspring.profiles.active=test

# Run tests with detailed output
.\gradlew test --info
```

### Development Tools
```bash
# Clean build artifacts
.\gradlew clean

# Generate sources (MapStruct mappers)
.\gradlew compileJava

# Check dependencies
.\gradlew dependencies

# View project tasks
.\gradlew tasks
```

## Architecture Overview

### Core Architecture Pattern
This is a **layered Spring Boot API** following Domain-Driven Design principles with:

- **Common Framework Layer**: Reusable abstractions for entities, DTOs, services, and repositories
- **Demo Domain Layer**: Specific business logic for User and Course entities
- **Security Integration**: OAuth2 JWT-based authentication with Keycloak
- **Audit Trail**: Hibernate Envers for entity versioning and change tracking
- **API Technologies**: REST endpoints, GraphQL queries, and comprehensive validation

### Key Architectural Components

#### Abstract Base Classes (Common Framework)
- `AbstractCommonEntity<T>`: Base entity with ID, version, and common fields
- `AbstractAutoIncrementCommonEntity`: Auto-increment ID variant
- `AbstractCommonDto<T>`: Base DTO with ID field
- `AbstractCommonService<ID, ENTITY, DTO, PATCH_DTO>`: CRUD operations with validation and soft deletion
- `CommonRepository<ID, ENTITY>`: Repository interface with Envers support
- `CommonMapper<ID, ENTITY, DTO, PATCH_DTO>`: MapStruct-based mapping interface

#### Entity Patterns
- **Soft Deletion**: Entities implementing `DeletableEntity` are marked as deleted rather than physically removed
- **Audit Tracking**: All entities are `@Audited` with Hibernate Envers for revision history
- **User Tracking**: Entities track `createdBy` and `updatedBy` relationships
- **Version Control**: Optimistic locking via `@Version` annotation

#### Service Layer Patterns
- Services extend `AbstractCommonService` for consistent CRUD operations
- Automatic validation using Bean Validation (`@Valid`) with custom constraints
- Security context integration via `SecurityUtils` for user tracking
- Role-based access control for sensitive operations (e.g., name changes require ADMIN role)

#### DTO and Mapping Strategy
- **Full DTOs**: Complete object representation for standard operations
- **Patch DTOs**: `JsonNullable<T>` fields for partial updates (PATCH operations)
- **Custom Validators**: `@NotBlankIfPresent` for conditional validation on patch operations
- **MapStruct Integration**: Automatic mapping between entities and DTOs with Lombok integration

#### API Design Patterns
- **RESTful Endpoints**: Standard CRUD + domain-specific search operations
- **GraphQL Integration**: Query-based data fetching with custom scalars
- **Audit Endpoints**: Revision history access per entity (`/revisions`, `/revisions/{id}`, `/revisions/latest`)
- **Security Annotations**: Method-level security with `@PreAuthorize`

### Technology Stack Integration
- **Database**: SQL Server with Hibernate ORM
- **Security**: Spring Security + OAuth2 Resource Server (Keycloak JWT)
- **Validation**: Bean Validation with custom validators
- **Documentation**: OpenAPI 3 (Swagger UI available)
- **Development**: Spring Boot DevTools with hot reloading
- **Testing**: Spring Boot Test + Testcontainers for integration testing

### Environment Configuration
- **Profiles**: `dev`, `test` configurations in `application-{profile}.yml`
- **External Config**: Environment variables for database and JWT issuer URIs
- **Database URL**: `jdbc:sqlserver://localhost:1433;databaseName=master`
- **JWT Issuer**: `http://localhost:8180/realms/dev` (Keycloak)

### Development Workflow
1. **API Testing**: Use `api.rest` file with environment variables in `http-client.env.json`
2. **Database Schema**: Auto-generated via `hibernate.ddl-auto=update`
3. **Security**: Obtain JWT tokens via Keycloak for USER/ADMIN roles
4. **GraphQL**: GraphiQL interface available for query development
5. **Audit Queries**: Use revision endpoints to track entity changes over time

### Windows Development Notes
- Always use `.\gradlew` (with backslash) for Gradle wrapper on Windows
- Use semicolons (`;`) to separate terminal commands in PowerShell
- SQL Server connection requires `encrypt=false;trustServerCertificate=true` for local development