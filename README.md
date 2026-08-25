# MyClothes — E-Commerce Backend

Backend application for an e-commerce platform developed with **Spring Boot** as part of the **Enterprise Application** university course.

The project provides a RESTful backend responsible for managing the main business logic of an online clothing store, including authentication, products, shopping carts, orders, customers, reviews, wishlists, discounts, and administrative operations.

## 🚀 Technologies

* **Java 17**
* **Spring Boot 3.3.1**
* **Spring Web** — REST API development
* **Spring Data JPA** — data persistence
* **Spring Security** — authentication and authorization
* **PostgreSQL** — relational database
* **JWT** — token-based authentication
* **Jakarta Validation** — request and data validation
* **MapStruct** — DTO/entity mapping
* **Lombok** — boilerplate code reduction
* **Maven** — dependency and build management

## ✨ Main Features

The backend exposes dedicated controllers and services for the main e-commerce functionalities:

* 🔐 **Authentication** — user authentication and security
* 👤 **Customer management** — customer-related operations
* 👕 **Product management** — product creation and management
* 🛒 **Shopping cart** — cart management
* 📦 **Orders** — order management and processing
* ⭐ **Reviews** — product reviews
* ❤️ **Wishlist** — user wishlists
* 🏷️ **Discount codes** — discount management
* 👨‍💼 **Administration** — administrative operations
* 💬 **Chat** — chat-related functionality

## 🏗️ Project Architecture

The application follows a layered architecture that separates responsibilities between different components:

```text
src/
└── main/
    └── java/
        └── com/ernestocesario/myclothes/
            ├── configurations/
            ├── controllers/
            ├── exceptions/
            ├── persistance/
            │   ├── DTOs/
            │   ├── entities/
            │   └── repositories/
            └── services/
                ├── interfaces/
                └── implementations/
```

### Controllers

The controller layer exposes the REST API and handles HTTP requests.

Available controllers include:

* `AuthController`
* `AdminController`
* `CustomerController`
* `ProductController`
* `CartController`
* `OrderController`
* `ReviewController`
* `WishlistController`
* `DiscountCodeController`
* `ChatController`

### Services

Business logic is organized into service interfaces and their corresponding implementations, keeping application logic separated from the API layer.

### Persistence

The persistence layer is divided into:

* **Entities** — database domain models
* **DTOs** — objects used to transfer data through the API
* **Repositories** — database access through Spring Data JPA

## 🔐 Security

The application uses **Spring Security** together with **JWT-based authentication** to secure the backend and manage authenticated requests.

Input validation is handled through **Jakarta Validation** and Spring Boot's validation support.

## 🗄️ Database

The application uses **PostgreSQL** as its relational database and **Spring Data JPA** for object-relational persistence.

Database configuration is handled through the application's `application.properties` file.

> Make sure to configure your PostgreSQL connection before starting the application.

## ⚙️ Getting Started

### Prerequisites

Make sure you have the following installed:

* Java 17 or later
* PostgreSQL
* Maven (or use the included Maven Wrapper)

### Clone the repository

```bash
git clone https://github.com/ernestocesario/MyClothes-backend-enterpriseApplication-university.git
cd MyClothes-backend-enterpriseApplication-university
```

### Configure the database

Create a PostgreSQL database and configure the required connection properties in:

```text
src/main/resources/application.properties
```

Set your database URL, username, password, and any other required application properties.

### Run the application

Using the Maven Wrapper:

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

**Windows**

```bash
mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed globally:

```bash
mvn spring-boot:run
```

## 📁 Project Structure

| Directory                  | Responsibility                         |
| -------------------------- | -------------------------------------- |
| `configurations`           | Application and security configuration |
| `controllers`              | REST API endpoints                     |
| `exceptions`               | Custom exception handling              |
| `persistance/entities`     | JPA entities                           |
| `persistance/DTOs`         | Data Transfer Objects                  |
| `persistance/repositories` | Database repositories                  |
| `services/interfaces`      | Service contracts                      |
| `services/implementations` | Business logic implementations         |

## 🎓 Academic Context

This project was developed as part of the **Enterprise Application** university course, with the goal of applying enterprise software development concepts and building a structured backend for an e-commerce application using the Spring ecosystem.

## 👨‍💻 Author

**Ernesto Cesario**

[GitHub Profile](https://github.com/ernestocesario)

---

⭐ If you find this project interesting, feel free to explore the repository and its implementation.
