# JudgeIt

A Java-based coding assessment and explainable grading system.

## Overview

JudgeIt is a coding assessment platform designed to manage programming problems and support explainable grading. This repository currently contains the **Problem Management module**, developed using Java, Spring Boot, Thymeleaf, and MySQL.

## Features

- Create programming problems.
- View the list of available problems.
- Edit existing problems.
- Delete problems with confirmation.
- Validate required fields and input lengths.
- Store problem details in MySQL using Spring Data JPA.
- Initialize sample problems when the database is empty.

## Technology Stack

- **Language:** Java 25
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Frontend:** Thymeleaf, HTML
- **Database:** MySQL
- **Data Access:** Spring Data JPA
- **Validation:** Jakarta Bean Validation

## Prerequisites

Install the following before running the project:

- Java Development Kit (JDK) 25
- MySQL Server 8.x or a compatible version
- Git

The project includes the Maven Wrapper, so a separate Maven installation is not required.

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/sabareeshwaran1302/JudgeIt.git
cd JudgeIt
```

### 2. Create the database

Log in to MySQL and execute:

```sql
CREATE DATABASE judgeit_db;
```

### 3. Configure the database connection

Create the file:

`src/main/resources/application-local.properties`

Add your own MySQL connection settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/judgeit_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Replace the username and password placeholders with your own local MySQL credentials.

**Security:** Never commit `application-local.properties` or share your database password. This local configuration file is excluded by `.gitignore`.

### 4. Run the application

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs at:

http://localhost:8081

### 5. Open Problem Management

Visit:

http://localhost:8081/problems

## Problem Management Operations

| Operation | HTTP Method | Endpoint |
|---|---|---|
| List problems | GET | `/problems` |
| Open new problem form | GET | `/problems/new` |
| Create problem | POST | `/problems` |
| Open edit form | GET | `/problems/edit/{id}` |
| Update problem | POST | `/problems/update/{id}` |
| Delete problem | POST | `/problems/delete/{id}` |

## Build the Project

To compile and package the application:

```powershell
.\mvnw.cmd clean package
```

To run the packaged JAR:

```powershell
java -jar target/judgeit-0.0.1-SNAPSHOT.jar
```

Ensure your MySQL database and local configuration are ready before starting the application.

## Project Structure

```text
JudgeIt/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/judgeit/
│   │   │   └── HomeController.java
│   │   ├── java/com/judgeit/judgeit/
│   │   │   ├── JudgeitApplication.java
│   │   │   ├── DataInitializer.java
│   │   │   ├── Problem.java
│   │   │   ├── ProblemController.java
│   │   │   └── ProblemRepository.java
│   │   └── resources/
│   │       ├── templates/
│   │       ├── application.properties
│   │       └── application-local.properties (local only)
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Current Development Scope

The Problem Management module provides the foundation for managing coding problems. Additional assessment, execution, and explainable grading capabilities may be developed and integrated as part of the wider project.

## Repository

GitHub: https://github.com/sabareeshwaran1302/JudgeIt
