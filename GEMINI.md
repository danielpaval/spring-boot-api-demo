# Project Overview

This is a Spring Boot API demo project that manages users and courses. It provides a RESTful API and a GraphQL API for interacting with the data. The project is built with Java 21 and Gradle.

## Main Technologies

*   **Backend:** Spring Boot, Java 21
*   **Data:** Spring Data JPA, SQL Server, Hibernate Envers for auditing
*   **API:** REST, GraphQL
*   **Security:** Spring Security with OAuth2/JWT (Keycloak)
*   **Testing:** JUnit 5, Testcontainers, Mockito
*   **Tooling:** Lombok, MapStruct, Gradle

## Architecture

The project follows a standard layered architecture:

*   **Controller Layer:** Exposes the REST and GraphQL endpoints.
*   **Service Layer:** Contains the business logic.
*   **Repository Layer:** Handles data access using Spring Data JPA.
*   **Entity Layer:** Defines the data model.
*   **DTO Layer:** Data Transfer Objects are used to transfer data between the layers and the client.

# Building and Running

## Prerequisites

*   Java 21
*   Gradle
*   Docker (for Testcontainers)
*   Keycloak (for authentication)

## Running the Application

1.  **Set up environment variables:** Create a `.env` file in the root directory and add the following variables:

    ```
    DATASOURCE_URL=jdbc:sqlserver://localhost:1433;databaseName=master;encrypt=false;trustServerCertificate=true;
    DATASOURCE_USERNAME=SA
    DATASOURCE_PASSWORD=<your_password>
    JWT_ISSUER_URI=http://localhost:8180/realms/dev
    ```

2.  **Run the application:**

    ```bash
    .\gradlew bootRun
    ```

The application will be available at `http://localhost:8080`.

## Testing the Application

The project includes several ways to test the application:

*   **Unit Tests:** Run the unit tests with the following command:

    ```bash
    .\gradlew test
    ```

*   **REST API Tests:** The `api.rest`, `testing-guide.md`, and `validation-tests.rest` files contain examples of how to test the REST API using a REST client that supports `.rest` files.

*   **GraphQL API:** The GraphQL API can be tested using the GraphiQL interface, which is available at `http://localhost:8080/graphiql` when the application is running.

# Development Conventions

*   **Code Style:** The project uses the standard Java code style.
*   **Lombok:** Lombok is used to reduce boilerplate code.
*   **MapStruct:** MapStruct is used for mapping between DTOs and entities.
*   **Testing:**
    *   Unit tests are located in the `src/test/java` directory.
    *   Testcontainers is used for integration tests that require a database.
*   **Commits:** Commit messages should follow the conventional commit format.
*   **Branching:** Feature branches should be created from the `main` branch.
