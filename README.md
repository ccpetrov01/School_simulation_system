🎓 School Simulation System
Secure • Scalable • Modern Spring Boot Platform
<p align="center"> <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=springboot" /> <img src="https://img.shields.io/badge/Spring_MVC-Architecture-blue?style=for-the-badge" /> <img src="https://img.shields.io/badge/PostgreSQL-Production_Ready-316192?style=for-the-badge&logo=postgresql" /> <img src="https://img.shields.io/badge/JWT-Security-red?style=for-the-badge" /> <img src="https://img.shields.io/badge/Flyway-Migrations-orange?style=for-the-badge&logo=flyway" /> <img src="https://img.shields.io/badge/Lombok-Clean_Code-green?style=for-the-badge&logo=lombok" /> </p>


🚀 Features
✔ 1. User Roles (JWT Security)

Admin

Can delete student and teacher accounts

Can view all data

Teacher

Can update their own profile

Can add grades, view students assigned to them

Student

Can update their own profile

Can view their grades and related information

JWT secures all endpoints, and only public endpoints allow registration/login.

🧱 Architecture Overview

The project uses a clean, maintainable structure:

✔ Entities (JPA)

Entities for Students, Teachers, Grades, Subjects, and more

Relationships include:

@OneToMany

@ManyToOne

@ManyToMany

@OneToOne (if used)

Lombok reduces boilerplate (getters, setters, constructors)

✔ DTO Layer

Only safe and required data is exposed

Sensitive/internal fields are hidden from API consumers

✔ Controllers & RestControllers

Split logically to maintain clean responsibilities

Responses standardized using ApiResponse (see section below)

✔ Services & Interfaces

Every service has a corresponding interface → easier maintenance & testing

Business logic stays in service layer

✔ Repository Layer

Uses Spring Data JPA

Clean, readable database interactions

🗃️ Database (PostgreSQL + Flyway)
✔ Flyway Migration

Schema and test data managed through versioned migrations

Ensures consistent database setup across environments

Clean version control of database structure

✔ PostgreSQL

Reliable and widely used relational database

The project was tested using:

200+ rows for fetching, inserting, updating, deleting

🧪 Testing (Postman)

All endpoints were tested via Postman

Tests include:

Valid requests

Error states

Authorization failures

Wrong credentials

Validation cases

❗ Custom Exception Handling (Verified Against Code)

You use a global exception system to produce user-friendly, descriptive messages.

Your custom exceptions include:

NotFoundException

AlreadyExistsException

GlobalException

others (as needed)

These exceptions are processed by a central handler that converts them into a unified API response format (see next section).

📦 Custom API Response System (Verified with Your Real Code)

Your project uses a fully custom and standardized response wrapper:

✔ Class: ccpetrov01.studentApp.responses.ApiResponse<T>
✔ Structure:
{
  "success": true/false,
  "message": "Text message",
  "data": { ... object or list ... },
  "errors": [ "error1", "error2" ]
}

✔ Fields (from your real class):

boolean success

String message

T data

List<String> errors

✔ Lombok-powered (verified)

@Data, @NoArgsConstructor, @AllArgsConstructor
→ All getters, setters, constructors, toString auto-generated.

✔ Success model:
public static <T> ApiResponse<T> success(String message, T data)

✔ Failure model:
public static <T> ApiResponse<T> failure(String message, List<String> errors)

✔ How controllers use it (example):
return ResponseEntity.ok(ApiResponse.success("Student created", dto));

✔ How your exception handler uses it (example):
return ResponseEntity.badRequest().body(
    ApiResponse.failure("Validation failed", List.of(e.getMessage()))
);


This makes the project extremely readable, standardized, and API-friendly.

🔐 Security (JWT)

Your project uses:

Custom JWT authentication

Role-based authorization

Secure password hashing

Token validation filters

Restricted endpoints

Public-only registration/login

Admins have full privileges; teachers & students have restricted action-based permissions.

📁 Project Technologies
Technology	Purpose
Spring Boot	Main framework
Spring MVC	Controller layer
Spring Data JPA	Database ORM
PostgreSQL	Database
Flyway	Database migrations
Lombok	Reduce boilerplate
JWT	Security & authentication
DTO Mapping	Data filtering
Custom Exceptions	Clear error messaging
Postman	API testing
📌 Summary

This project is a fully functional school management backend system featuring:

Proper layered architecture

Clean DTO design

Exception-safe code

Unified API responses

Strong JWT security

Fully working database layer

200+ rows tested

Postman-verified endpoints

Admin/Teacher/Student roles
