# Project OO Systems Development - The Library manager

**Description:**

This project showcases a microservices architecture with two services: one built with REST and the other with RPC (gRPC). Each service uses a unique database (SQL for the REST service and NoSQL(here MongoDb) for the gRPC service). The project adheres to the specified Java coding rules and is documented using the Open API specification (Swagger). It is also accompagnied with a responsive frontend.
Done by: Manal And Quentin
---

## Table of Contents

1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Services and Endpoints](#services-and-endpoints)
4. [Database Structure](#database-structure)
5. [Setup and Installation](#setup-and-installation)
6. [Testing](#testing)
7. [Usage and Examples](#usage-and-examples)
8. [Screenshots and Documentation](#screenshots-and-documentation)
9. [Coding Standards](#coding-standards)
10. [Contributors](#contributors)

---

## Overview

This project involves a library management application, allowing users to interact with book data using both REST and gRPC services.

## Architecture

The project is split into two services:
1. **REST Service**: Handles CRUD operations for books using an SQL database (MySQL).
2. **gRPC Service**: Manages borrowed books and checks book availability using a NoSQL database (MongoDB).

Both services communicate over HTTP, and each service is documented and accessible via Swagger. **phooootoooo**

---

## Services and Endpoints

### REST Service (Book Management)

**Base URL**: `http://localhost:8080/api/books`

#### Endpoints:
- **GET** `/api/books` - Fetch all books
- **POST** `/api/books` - Add a new book
- **PUT** `/api/books/{id}` - Update a book by ID
- **DELETE** `/api/books/{id}` - Delete a book by ID
- **GET** `/api/books/search` - Search books by criteria (title, author, isbn)

#### CURL Commands

```bash
# Fetch all books
curl -X GET http://localhost:8080/api/books

# Add a book
curl -X POST http://localhost:8080/api/books -H "Content-Type: application/json" -d '{"title": "1984", "author": "George Orwell", "isbn": "9780451524935", "available": true}'

# Update a book by ID
curl -X PUT http://localhost:8080/api/books/1 -H "Content-Type: application/json" -d '{"title": "Animal Farm", "author": "George Orwell", "isbn": "9780451526342", "available": true}'

# Delete a book by ID
curl -X DELETE http://localhost:8080/api/books/1
```

### gRPC Service (Borrowed Books Management)

**Base URL**: `localhost:6565`

#### RPC Methods:
- **borrowBook** - Allows a user to borrow a book.
- **returnBook** - Allows a user to return a borrowed book.
- **checkBookAvailability** - Checks if a book is currently available for borrowing.

**phooootoooo of gRPC methods here**

---

## Database Structure

### MySQL (Book Database)

| Field     | Type         | Description               |
|-----------|--------------|---------------------------|
| id        | BIGINT       | Primary Key               |
| title     | VARCHAR(255) | Title of the book         |
| author    | VARCHAR(255) | Author of the book        |
| isbn      | VARCHAR(255) | ISBN of the book          |
| available | BOOLEAN      | Availability of the book  |

### MongoDB (Borrowed Book Database)

| Field      | Type   | Description                     |
|------------|--------|---------------------------------|
| id         | String | Unique identifier for borrowals |
| bookId     | String | ID of the borrowed book         |
| userId     | String | ID of the user                  |
| borrowDate | String | Date when the book was borrowed |

---

## Setup and Installation

### Prerequisites

- **Java 17+**
- **MongoDB**
- **MySQL**
- **gRPC and Spring Boot**

### Installation Steps

1. **Clone the Repository**:
   ```bash
   git clone [repository-url]
   cd Project-OO-Systems-Development
   ```

2. **Configure the Databases**:
   - Start MySQL and MongoDB services.
   - Create necessary databases and users.

3. **Build and Run the Application**:
   ```bash
   # Compile and build the project
   ./gradlew build

   # Run the REST and gRPC servers
   ./gradlew bootRun
   ```

---

## Testing

The project includes comprehensive tests covering:
- Unit tests for core service functionalities
- Integration tests for REST and gRPC endpoints
- tests for the data flow

**Run Tests**:
```bash
./gradlew test
```

### Testing Files Structure

- `/src/test/java/com/example/OODevProject` - Contains unit tests for REST and gRPC services

---

## Usage and Examples

## Frontend Service Overview

The frontend service is developed with **Vue.js** and styled using **Tailwind CSS** and **daisyUI**, providing a modern, minimalistic look and user-friendly interface for library staff and users. This design enables smooth navigation and efficient interaction with the system.

### Key Features

- **Book Management**: Allows users to add, edit, or delete books in the library.
- **Search and Availability Check**: Users can search for books by title, author, or ISBN and check their availability.

<img width="458" alt="Capture d’écran 2024-11-03 230841" src="https://github.com/user-attachments/assets/6c7b484d-ee40-4d2c-9925-d1c7b103c47f">

<img width="456" alt="Capture d’écran 2024-11-03 230854" src="https://github.com/user-attachments/assets/0656f384-1262-4623-a6a2-b1652574a100">


<img width="449" alt="Capture d’écran 2024-11-03 230911" src="https://github.com/user-attachments/assets/ad6e413a-33bf-40d3-b033-b965c1f5b8fa">

---

## Documentation

### Swagger UI Documentation

- **Swagger URL**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Screenshots
<img width="830" alt="Capture d’écran 2024-11-03 230632" src="https://github.com/user-attachments/assets/327a6c58-1271-45de-a92b-c4b1f5da9371">

<img width="842" alt="Capture d’écran 2024-11-03 230540" src="https://github.com/user-attachments/assets/5d3ab541-b02e-4de6-b026-a515e4f16d1b">


<img width="831" alt="Capture d’écran 2024-11-03 230647" src="https://github.com/user-attachments/assets/038d5153-efec-41b2-946b-1170a8c8ec1b">


---

## Docker Deployment

The project includes Docker configurations for easy deployment of both backend and frontend services. The root directory contains a `docker-compose.yml` file that facilitates multi-container deployment, allowing the services to be deployed and managed together seamlessly.

### Building the Project

To build the project, navigate to the root directory and run:
```bash
docker compose build
```

### Running the Project

To run the project, execute:
```bash
docker compose up
```

For building and running the project simultaneously, use:
```bash
docker compose up --build
```

### Pushing to Docker Hub

1. **Log in to Docker Hub**:
   ```bash
   docker login
   ```

2. **Tag Docker Images**:
   ```bash
   docker tag library-frontend quentin1312/library-frontend:latest
   docker tag library-backend quentin1312/library-backend:latest
   ```

3. **Push to Docker Hub**:
   Use `docker push` to upload the images to Docker Hub for shared access.
   ```bash
   docker push quentin1312/library-frontend:latest
   docker push quentin1312/library-backend:latest
   ```

### Using the Application

**Retrieving the Project from DockerHub**: To pull the pre-built images from DockerHub, run:
```bash
docker pull quentin1312/library-frontend:latest
docker pull quentin1312/library-backend:latest
```

Once retrieved, use `docker compose up` to start the application if the `docker-compose.yml` file is available.

**Running Without `docker-compose.yml`**:

If the `docker-compose.yml` file is not available, use the following commands to run the containers individually:
```bash
docker run -d --name backend --network app-network -p 8080:8080 quentin1312/library-backend:latest
docker run -d --name frontend --network app-network -p 5173:5173 quentin1312/library-frontend:latest
```

This configuration sets up the application on a Docker network (`app-network`) and maps the backend to port `8080` and the frontend to port `5173`.

--- 


## Coding Standards

The project follows [Java Coding Rules](https://github.com/charroux/JavaCodingRules) for consistency and maintainability.

Key guidelines observed:
- Naming conventions and best practices.
- Exception handling with clear and actionable logs.
- Modular and well-documented code.

---

## Contributors

- **Manal GHARRABOU**
- **Quentin Lauriot**

--- 

