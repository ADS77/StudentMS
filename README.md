# Student Management System - Spring Boot Demo

A comprehensive Student Management System built with **Spring Boot** to demonstrate core Spring concepts including **Dependency Injection**, **Spring Data JPA**, **File Operations**, **Threading**, and **RESTful APIs**.

## 🚀 Features

### Core Features
- **CRUD Operations** for student management
- **File Upload/Download** for student profile pictures
- **CSV Import/Export** functionality
- **Report Generation** in text format
- **Asynchronous Processing** with Spring's `@Async`
- **Threading Demonstrations** with custom threads and ExecutorService
- **Scheduled Tasks** for background operations
- **Data Validation** with Bean Validation
- **Global Exception Handling**

### Advanced Features
- **Thread-safe Operations** with synchronization
- **Batch Processing** with multiple threads
- **File I/O Operations** using Java NIO.2
- **Custom Thread Pool Configuration**
- **Real-time Processing Statistics**

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 17** or higher installed
- **Maven 3.6+** installed
- **Docker** and **Docker Compose** (for database)
- **Git** (to clone the repository)
- **Postman** or similar tool for API testing (optional)

### Verify Prerequisites

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Docker version
docker --version
docker-compose --version
```

## 🏃‍♂️ Getting Started

### Clone the Repository

```bash
git clone https://github.com/ADS77/StudentMS
cd StudentMS
```
## 🗄️ Database Setup

### Start PostgreSQL with Docker

Navigate to the Docker directory and start the database:

```bash
cd Docker
docker-compose up -d postgres
```

### Verify Database Connection

```bash
# Check if container is running
docker ps

# Connect to PostgreSQL (optional)
docker exec -it student-db psql -U postgres -d student
```

### Alternative: Use H2 Database (In-Memory)

If you prefer to use H2 for testing, update `application.properties`:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

## 🏃‍♂️ Running the Application

### Method 1: Using Maven

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

### Method 2: Using JAR

```bash
# Package the application
mvn clean package

# Run the JAR
java -jar target/student-management-system-1.0.0.jar
```

### Method 3: Using IDE

1. Import the project into your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Run the `StudentManagementSystemApplication.java` main class



