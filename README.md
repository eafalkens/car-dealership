# Car Dealership - School Assignment

## Introduction
This project is a modern web application developed with **Spring Boot** and **Spring MVC**. Its purpose is to manage a collection of cars in a dealership's system. The application uses **HTML pages** (Thymeleaf/JTE) for the user interface, where users can perform CRUD operations (Create, Read, Update, Delete).

## Features
The application supports the following functionality:
* **List all cars:** An overview page displaying all cars in stock.
* **Create new cars:** A form to add a new car with validation.
* **Update cars:** The ability to edit information for an existing car.
* **Delete cars:** A function to remove a car from the system.
* **Validation:** Clear error messages in forms if data is missing or incorrect.

### Domain Model (Entity)
**Class:** `Car`
Data is persisted in the database via JPA. A car entity includes at least:
* **ID:** (Auto-generated)
* **Brand:** The car's manufacturer (e.g., Volvo)
* **Model:** The car's model name (e.g., V60)
* **Description:** A short text about the car
* **Year:** The car's manufacturing year
* **Registration Number:** (Domain-specific attribute)

### Layered Architecture
The application is divided into the following layers for a clean structure:
1. **Repository:** `CarRepository` (Extends `ListCrudRepository`, handles database communication).
2. **Service:** `CarService` (Handles business logic and calls the Repository).
3. **Controller:** `CarController` (Handles HTTP requests and returns HTML views).

### DTO Pattern & Mapper
To separate the database model from what is displayed or submitted in forms, DTOs are used:
* `CreateCarDTO`: Used in the form when creating a car.
* `UpdateCarDTO`: Used in the form when updating a car.
* `CarDTO`: Used to display car data in lists.
* **Mapper:** A class that converts data between the `Car` (Entity) and the DTOs.

## Tech Stack
* **Framework:** Spring Boot, Spring MVC
* **Database:** Spring Data JPA (with e.g., H2 or MySQL)
* **Validation:** Jakarta Bean Validation (Hibernate Validator)
* **Views:** HTML with Thymeleaf (or JTE)
* **Testing:** JUnit, Mockito

## To-Do (Workflow)
1. [ ] Create `Car` entity and `CarRepository`.
2. [ ] Create Service layer and Mapper.
3. [ ] Implement Controller and HTML views (List, Create, Update, Delete). 
4. [ ] Add validation and error handling.
5. [ ] Write unit tests.