<<<<<< feature/v1.2-ventas
# Sistema de Gestión Empresarial v1.1

## Descripción del Proyecto

Sistema de gestión empresarial desarrollado en Java orientado a RRHH, ventas, inventario y análisis de información.

El proyecto evolucionó desde una versión inicial enfocada en autenticación y gestión de usuarios hacia una solución empresarial más completa, incorporando módulos comerciales, persistencia avanzada y visualización de datos mediante Power BI.

La aplicación fue desarrollada utilizando arquitectura por capas, conexión a base de datos MariaDB/MySQL y control de versiones con Git y GitHub.

---

# Tecnologías Utilizadas

* Java
* Java Swing
* MariaDB / MySQL
* Maven
* Git & GitHub
* Power BI
* Ubuntu
* Windows
* Visual Studio Code

---

# Funcionalidades Principales

## Gestión de Usuarios

* Inicio de sesión
* Control por roles
* Validación de usuarios
* Gestión administrativa

## Gestión Comercial

* Registro de ventas
* Control de inventario
* Persistencia de productos
* Gestión de solicitudes
* Validaciones empresariales

## Inventario

* Inventario compartido
* Actualización de stock
* Gestión centralizada
* Flujo de validaciones

## Power BI

* Integración analítica
* Dashboard ejecutivo
* Reportes de ventas
* Visualización de información empresarial

---

# Arquitectura del Proyecto

El sistema fue desarrollado utilizando arquitectura por capas:

## Modelo

Representación de entidades del sistema.

## DAO

Persistencia y consultas SQL.

## Servicio

Lógica de negocio y validaciones.

## UI

Interfaz gráfica desarrollada con Java Swing.

---

# Capturas del Sistema

## Panel Principal

![Panel Principal](docs/images/panel-principal.png)

## Login

![Login](docs/images/login.png)

## Gestión de Usuarios

## Gestión Solicitudes

![Usuario](docs/images/admin.png)

## Gestión RRHH

![Usuario](docs/images/rrhh.png)

## Gestión Ventas e Inventario

![Usuario](docs/images/gerente_1.png)
![Usuario](docs/images/gerente_2.png)

## Gestión Venta

![Usuario](docs/images/vendedor_1.png)
![Usuario](docs/images/vendedor_2.png)

## Power BI

![Power BI](docs/images/powerbi.png)

---

# Videos del Proyecto

## Video Demo v1.0

[https://www.youtube.com/watch?v=jih3bNQ8UdI](https://www.youtube.com/watch?v=jih3bNQ8UdI)

## Video Ubuntu v1.1

[https://youtu.be/pkwZILdB7-Y?si=DyOZ5_8fZtCsk9r0](https://youtu.be/pkwZILdB7-Y?si=DyOZ5_8fZtCsk9r0)

## Video Windows + Power BI v1.1

[https://youtu.be/rSWgHgdI0ZM?si=A5jFfastGb-ru-uS](https://youtu.be/rSWgHgdI0ZM?si=A5jFfastGb-ru-uS)

---

# Power BI Dashboard

[https://app.powerbi.com/groups/me/reports/8a5e2020-15a6-4af8-8595-be4e50deb41b/a5a71bfffa2413879405?experience=power-bi](https://app.powerbi.com/groups/me/reports/8a5e2020-15a6-4af8-8595-be4e50deb41b/a5a71bfffa2413879405?experience=power-bi)

---

# GitHub

[https://github.com/alejandrconductor-sys/sistema-gestion-usuarios-java](https://github.com/alejandrconductor-sys/sistema-gestion-usuarios-java)

---

# LinkedIn

[https://www.linkedin.com/in/rafael-alejandro-marquez-araujo-4276093b7/](https://www.linkedin.com/in/rafael-alejandro-marquez-araujo-4276093b7/)

---

# Instalación y Ejecución

## Clonar repositorio

```bash
git clone https://github.com/alejandrconductor-sys/sistema-gestion-usuarios-java.git
```

## Abrir proyecto

Abrir el proyecto en Visual Studio Code.

## Ejecutar

Compilar y ejecutar el proyecto utilizando Maven.

---

# Evolución del Proyecto

## Versión 1.0

* Sistema RRHH básico
* Gestión de usuarios
* Autenticación
* Roles
* Persistencia inicial

## Versión 1.1

* Gestión comercial
* Ventas
* Inventario compartido
* Solicitudes
* Integración Power BI
* Dashboard principal profesional
* Integración GitHub y documentación

---

# Objetivo del Proyecto

El objetivo principal fue construir un sistema empresarial orientado a escenarios reales de negocio, aplicando:

* Arquitectura por capas
* Persistencia de datos
* Validaciones empresariales
* Gestión de inventario
* Análisis de información
* Control de versiones
* Presentación profesional de proyecto

# Descarga y Pruebas

La carpeta `release/` contiene:

- Archivo ejecutable `.jar`
- Backup SQL de la base de datos
- Guía de ejecución local

Contenido:

release/
├── sistema-gestion-v1.1.jar
├── backup_bd_v1.1.sql
└── README_EJECUCION.txt

Esto permite probar el sistema de manera local.

---

# Desarrollador

Rafael Marquez

Desarrollador enfocado en Java, bases de datos y análisis de información empresarial.


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
>>>>>> main
