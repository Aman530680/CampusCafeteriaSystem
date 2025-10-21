🍽️ Campus Cafeteria Management System (CCMS)

A full-stack web application built using Spring Boot and Thymeleaf for managing cafeteria menu items, recording daily sales, and handling user registration/login. This project utilizes a standard relational database approach (MySQL) with Spring Data JPA for persistence.

🚀 Technologies Used

Backend Framework: Spring Boot 3.x

Web Layer: Spring MVC

Security: Spring Security (for login/logout and basic authentication)

Database: MySQL

Persistence: Spring Data JPA / Hibernate

Templating: Thymeleaf

Build Tool: Maven

Language: Java 21

🛠️ Prerequisites

Before running this application, ensure you have the following installed:

Java Development Kit (JDK) 21 or higher.

Maven 3.x

MySQL Database server running locally (e.g., MySQL 8.x).

⚙️ Setup and Installation

Follow these steps to get the CCMS up and running on your local machine.

1. Database Configuration

You must create a MySQL database and update the connection details in src/main/resources/application.properties.

Create Database: Run the following SQL command in your MySQL client:

CREATE DATABASE cafeteria2;


Update application.properties: Edit the file to match your database credentials.

# 2. MySQL Datasource Configuration (UPDATE THESE VALUES)
spring.datasource.url=jdbc:mysql://localhost:3306/cafeteria2?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Asha530680@  <-- UPDATE THIS!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# DDL-Auto will create all necessary tables automatically
spring.jpa.hibernate.ddl-auto=update


2. Run the Application

Navigate to the project root directory in your terminal and use the Maven wrapper:

# Clean, compile, and package the application
./mvnw clean package

# Run the application (it will start on port 8085 as configured)
./mvnw spring-boot:run


Alternatively, you can run the CafeteriaTrackerApplication.java file directly from your IDE (IntelliJ, Eclipse, VS Code).

🖥️ Usage

The application will be accessible at: http://localhost:8085/

Initial Access

Register a User: Navigate to /signup and create an account, setting the role to STAFF to access the management dashboard.

Login: Use your registered credentials on the /login page.

Key Features

URL

Access

Description

/dashboard

Authenticated

System overview and key statistics.

/menu

Staff Only

CRUD operations for managing menu items (add, edit, delete).

/sales

Staff Only

Record new sales transactions and view historical sales data.

/users

Staff Only

View and manage registered user accounts.

✍️ Contribution

If you would like to contribute to this project, please feel free to fork the repository and submit a pull request!
