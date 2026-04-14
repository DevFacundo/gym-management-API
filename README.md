# 🏋️ Gym Management API

API robusta para la gestión administrativa de complejos deportivos, desarrollada con un enfoque en código limpio, escalabilidad y mantenimiento.

---

### 🚀 Tecnologías Principales

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.x
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** PostgreSQL / MySQL
* **Seguridad:** Spring Security & JWT
* **Testing:** JUnit 5 & Mockito
* **Documentación:** Swagger / OpenAPI

---

### 🏗️ Arquitectura y Patrones de Diseño

El proyecto sigue una estructura modular orientada a servicios para facilitar el desacoplamiento:

* **DTO Pattern:** Utilizado para transferir datos entre capas, evitando exponer las entidades de la base de datos directamente.
* **Mappers:** Implementación de lógica de conversión entre entidades y DTOs para mantener la integridad de la API.
* **Service Layer:** Toda la lógica de negocio se encuentra centralizada en servicios, asegurando que los controladores se mantengan livianos.
* **Custom Exceptions:** Manejo global de errores para proporcionar respuestas claras y consistentes al cliente.

---

### 📁 Estructura del Proyecto

```text
src/main/java/com/example/gym_management
├── dto             # Objetos de transferencia de datos (Request/Response)
├── enums           # Roles y tipos definidos
├── exception       # Manejadores de excepciones personalizadas
├── mapper          # Lógica de mapeo de objetos
├── model           # Entidades JPA
├── repository      # Interfaces de acceso a datos
└── service         # Lógica de negocio e interfaces
🛠️ Funcionalidades
Gestión de Miembros: Registro completo, perfiles de salud y estados de cuenta.

Control de Membresías: Creación y asignación de planes (mensual, anual, etc.).

Historiales de Salud: Seguimiento de patologías y condiciones físicas de los socios.

Sistema de Pagos: Registro y trazabilidad de transacciones de membresías.

Cronograma de Clases: Gestión de horarios y asignación de actividades.

🛡️ Calidad y Testing
Este proyecto pone un fuerte énfasis en la fiabilidad del código:

Pruebas Unitarias: Validación de la lógica de servicios utilizando JUnit 5.

Mocking: Aislamiento de componentes mediante Mockito para asegurar tests precisos.

Validaciones: Uso de Spring Validation para asegurar la integridad de los datos de entrada.
