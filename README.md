# API Gestion Inventario (Java)
### Descripción.
La API de Gestión de Inventario es un servicio backend diseñado para administrar de manera eficiente el flujo de productos, el catálogo de inventario, proveedores y usuarios dentro de una organización. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los recursos principales y ofrece un sistema de auditoría y trazabilidad mediante el registro de movimientos de entrada y salida de mercancía.

### Intengrantes.
- Katherine Jeanmillette Santos Sermeño
- Audiel Isaac Terán Morales
- Denilson Alfredo Vega Granadino
- Carlos Alfredo Ayala Mejía
- Jefferson Gerardo Quezada López

### Actores del Sistema
 - Administrador (ADMIN): Usuario con acceso total al sistema. Encargado de la gestión de usuarios, proveedores, categorías y catálogo de productos, además de registrar y consultar movimientos e historial de stock.
 - Operador (OPERADOR): Usuario enfocado en la operativa diaria. Registra entradas/salidas de inventario y realiza consultas sobre productos, stock e historial de movimientos. No cuenta con permisos para crear, editar o eliminar entidades base del sistema (usuarios, proveedores, categorías o productos).

### Estructura del proyecto 

```
.
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── docs/
│   ├── Casos de Uso/
│   │   └── .gitkeep
│   ├── Diagrama Entidad-Relación/
│   │   ├── .gitkeep
│   │   └── ER-gestion-inventario.png
│   └── Diagrama de Clases UML/
│       └── codigo_diagramaUML.puml
├── src/
│   ├── main/
│   │   ├── java/com/grupo7/GestionInventarioApi/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── exception/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   │   ├── enums/
│   │   │   │   └── .gitkeep
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── GestionInventarioApiApplication.java
│   │   └── resources/
│   │       ├── db/migration/
│   │       └── application.properties
│   └── test/java/com/grupo7/GestionInventarioApi/
│       └── GestionInventarioApiApplicationTests.java
├── .gitattributes
├── .gitignore
├── README.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```
## Diagramas 
### Casos de uso
![](docs/Casos%20de%20Uso/diagrama_casos_uso2.png)
