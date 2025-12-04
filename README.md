🎓 School Simulation System
Secure • Scalable • Modern Spring Boot Platform
<p align="center"> <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=springboot" /> <img src="https://img.shields.io/badge/Spring_MVC-Architecture-blue?style=for-the-badge" /> <img src="https://img.shields.io/badge/PostgreSQL-Production_Ready-316192?style=for-the-badge&logo=postgresql" /> <img src="https://img.shields.io/badge/JWT-Security-red?style=for-the-badge" /> <img src="https://img.shields.io/badge/Flyway-Migrations-orange?style=for-the-badge&logo=flyway" /> <img src="https://img.shields.io/badge/Lombok-Clean_Code-green?style=for-the-badge&logo=lombok" /> </p>
🚀 Overview

This project is a complete Spring Boot + Spring MVC Student Management System built with real-world standards for:

🔄 Clean modular separation (Entities, DTOs, Services, Repositories, Controllers)

🔐 JWT-based security & role authorization

📦 Custom API responses

❗ Custom global exception handling

🗃️ Flyway-powered SQL migrations

🧪 200+ Postman tests

🐘 PostgreSQL database

It follows professional backend design patterns and focuses heavily on maintainability, scalability, and clarity.

🏗️ Architecture Overview
┌─────────────────────────────────────────────┐
│                  API Layer                   │
│  REST Controllers → APIResponse builder      │
├─────────────────────────────────────────────┤
│               Service Layer                  │
│ Business Logic | Validation | Rules          │
├─────────────────────────────────────────────┤
│              Repository Layer                │
│ Spring Data JPA | PostgreSQL                 │
├─────────────────────────────────────────────┤
│ Entities | DTOs | Mappers | Lombok           │
│ OneToMany, ManyToMany, etc.                  │
└─────────────────────────────────────────────┘

🧩 Data Model (Entities & Relations)

This project contains multiple entities connected via:

@OneToMany

@ManyToOne

@ManyToMany

Cascade behaviors

Lazy/Fetch type optimizations

The domain is fully normalized, using DTOs to ensure clean, safe data exposure.

📤 DTO Layer (Secure Data Output)

DTOs ensure that:

✔ Only required data is returned
✔ Internal relations & sensitive fields stay hidden
✔ Response bodies remain lightweight

Every controller outputs APIResponse<T> instead of raw entity objects.

📬 Custom APIResponse Class (Unified Response Format)

All controller responses go through a centralized custom response class, improving:

API consistency

Error readability

Client-side integration

✔ Example APIResponse format:
{
  "success": true,
  "message": "Student created successfully",
  "data": {
    "id": 12,
    "name": "John Doe"
  },
  "timestamp": "2025-01-22T15:01:32"
}


Features in APIResponse:

success boolean

message field for user-friendly explanation

generic data object

timestamp for debugging

❗ Custom Exception System

The application includes a dedicated global exception layer powered by:

✔ NotFoundException

Thrown when an entity does not exist.

✔ AlreadyExistsException

Used when attempting to create an already existing entity.

✔ GlobalExceptionHandler

A Spring @ControllerAdvice class that:

Catches all exceptions

Returns a formatted APIResponse

Converts technical errors into user-friendly messages

Avoids exposing sensitive backend stack traces

✔ Example error response:
{
  "success": false,
  "message": "Student with ID 45 not found",
  "timestamp": "2025-01-22T12:15:21"
}

🔐 Security Layer (JWT)

Security is fully handled using:

✔ Login / Register endpoints

Generate a JWT upon successful authentication.

✔ Role-based authorization

Admin

Manage all students and teachers

Delete accounts

Teacher

Update own profile

Add grades to students

Student

Update only own data

View only personal info

✔ JWT validation includes:

Token expiration checks

Signature validation

Role extraction

Access control per endpoint

No endpoint is accessible without valid token (except login/register).

🧪 Testing (Postman)

The API is validated with 200+ Postman tests, covering:

Authentication

DTO correctness

Permissions by role

Exception handling

Token expiration

CRUD operations

Data integrity

🐘 Database (PostgreSQL + Flyway)
✔ PostgreSQL is used for:

High performance

Real relational modeling

Safe transaction handling

✔ Flyway migration files include:

Table creation

Constraints

Seed data

Schema evolution

Example structure:

src/main/resources/db/migration/
 ├─ V1__create_students.sql
 ├─ V2__create_teachers.sql
 ├─ V3__relations.sql
 ├─ V4__grades.sql

▶️ Running the Project
1. Create the database:
CREATE DATABASE student_app;

2. Configure application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/student_app
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

3. Start the project:
mvn spring-boot:run

📡 Example API Endpoints
🔑 Authentication
POST /api/auth/register
POST /api/auth/login

🎓 Students
GET    /api/students
POST   /api/students
PUT    /api/students/{id}
DELETE /api/students/{id}     (ADMIN ONLY)

👨‍🏫 Teachers
GET    /api/teachers
PUT    /api/teachers/{id}     (ADMIN or TEACHER OWNER)
POST   /api/teachers/{id}/grades

🛠️ Technologies Used
Tech	Description
Spring Boot	Backend foundation
Spring MVC	Controllers & routing
Spring Security + JWT	Authorization & authentication
JPA / Hibernate	ORM & DB operations
Lombok	Eliminates boilerplate
PostgreSQL	Production DB
Flyway	Database migrations
Postman	API testing
Maven	Build & dependencies
🧭 Future Improvements

Swagger/OpenAPI documentation

Docker blueprint

Pagination & filtering

Extended role management

Email notifications
