# Health Care – Appointment App (Backend)

## Overview

This project is a backend system for a healthcare appointment booking application.
It is built with Spring Boot and provides a REST API that enables patients, caregivers, and administrators to manage bookings, availability, and user data in a secure and structured way.

The backend focuses on:

* secure authentication and authorization
* clear business logic separation
* data integrity and validation
* scalable and maintainable architecture

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA / Hibernate
* PostgreSQL
* Maven

---

## Features

* User registration and login
* JWT-based authentication (Bearer + HttpOnly cookie support)
* Email verification with token
* Role-based access control (USER, ADMIN, CAREGIVER)
* Booking management (create, view, delete)
* Availability management
* Feedback system
* User management with support for anonymization (soft delete) and hard delete
* Centralized error handling

---

## Architecture

The backend follows a layered architecture:

* **Controllers** → handle HTTP requests and responses
* **Services** → contain business logic
* **Repositories** → manage database operations
* **DTOs** → define API contracts and protect internal models

The system uses a relational database (PostgreSQL) and JPA/Hibernate for ORM.

---

## Security

Security is a core part of the system:

* JWT authentication with stateless session handling
* Role-based authorization using Spring Security
* Protected endpoints with `@PreAuthorize`
* Email verification before account activation
* Sensitive data is excluded from API responses
* Passwords are encrypted and never logged
* Support for both Bearer token and HttpOnly cookie

---

## Testing & Quality

Testing focuses mainly on the service layer where business logic is implemented.

* JUnit 5 for unit testing
* Mockito for mocking dependencies
* JaCoCo for code coverage
* Maven Surefire for test reports

### Coverage

* Service layer coverage: ~86%
* Total project coverage: ~46%

Tests follow the AAA pattern (Arrange, Act, Assert) and are integrated into the CI pipeline.

---

## CI/CD

The project uses GitHub Actions for continuous integration:

* automatic build
* automated test execution
* code coverage reporting (JaCoCo)
* test reports (Surefire)

Pull requests can only be merged if the pipeline passes successfully.

---

## Notes

This backend is part of a fullstack system.
The frontend (React) is not included in this repository.

---

## Author

Developed as part of a team project with focus on backend architecture, security, testing, and CI/CD.
