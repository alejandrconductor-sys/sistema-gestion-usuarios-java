# User Management System - Java

User Management System developed in Java, designed for Human Resources departments to manage users, roles and account states within an organization.

The project demonstrates the implementation of Object-Oriented Programming principles, a layered architecture and database persistence using SQL.

---

## Technologies

- Java
- JDBC
- MariaDB
- SQL
- Swing (GUI)
- Git
- Linux (Ubuntu)
- Visual Studio Code

---

## System Architecture

The system follows a layered architecture that separates responsibilities across different components:

Presentation Layer  
- Java Swing graphical interface  
- User interaction and event handling

Business Logic Layer  
- Service classes responsible for application logic

Data Access Layer (DAO)  
- Database communication through JDBC

Database  
- Relational database implemented in MariaDB using SQL

This architecture improves maintainability, scalability and code organization.

---

## Features

- User registration
- User management
- Role-based access control
- User activation and deactivation
- Secure login system
- SQL database persistence
- Graphical user interface for administration

---

## Project Structure

src/
│
├── modelo  
│   └── Usuario.java
│
├── servicio  
│   └── UsuarioServicio.java
│
├── dao  
│   └── UsuarioDAO.java
│
├── conexion  
│   └── ConexionBD.java
│
├── ui  
│   ├── LoginFrame.java
│   ├── MenuPrincipalFrame.java
│   └── GestionUsuariosDialog.java
│
└── Main.java

---

## System Demonstration

Video demonstration of the system:

https://www.youtube.com/watch?v=jih3bNQ8UdI

---

## Author

Rafael Márquez  
Junior Java Developer
