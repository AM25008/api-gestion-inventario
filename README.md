# API Gestion Inventario (Java)
### Descripción.
La API de Gestión de Inventario es un servicio backend diseñado para administrar de manera eficiente el flujo de productos, el catálogo de inventario, proveedores y usuarios dentro de una organización. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los recursos principales y ofrece un sistema de auditoría y trazabilidad mediante el registro de movimientos de entrada y salida de mercancía.

### Intengrantes.
- Katherine Jeanmillette Santos Sermeño
- Audiel Isaac Terán Morales
- Denilson Alfredo Vega Granadino
- Carlos Alfredo Ayala Mejía
- Jefferson Gerardo Quezada López

## Arquitectura del modelo de datos
El sistema estructura el dominio del negocio mediante seis entidades principales: 
- Productos
- Categorías
- Proveedores
- ProductoProveedor(relación N:M)
- Movimientos
- Usuarios.

### Reglas de negocio y lógica de operacion
- Control de Acceso por Roles: El sistema identifica al usuario mediante su ID antes de cada acción. El Administrador (ADMIN) cuenta con permisos completos de gestión, mientras que el Operador (OPERADOR) está enfocado en la operativa diaria y consultas. Si un operador intenta realizar acciones no autorizadas(como eliminar productos o gestionar usuarios), el sistema devuelve un error 403.

- Preservación de Trazabilidad: Las entidades principales poseen el atributo activo. Si se intenta eliminar físicamente un producto, proveedor o categoría que ya posee movimientos o asociaciones registradas, el sistema rechaza la eliminación de la base de datos y aplica una baja lógica (marcándolo como inactivo) para evitar huérfanos y mantener el historial protegido. 

- Control de Stock: Al registrar una SALIDA, el sistema valida que la cantidad solicitada no supere el stock actual; de ser así, rechaza la operación con un error de stock insuficiente. Además, incluye un mecanismo de consulta para filtrar productos cuyo stock haya alcanzado o caído por debajo de su stock mínimo.

- Protección de Administración: El sistema bloquea los intentos de un administrador por desactivar su propia cuenta, previniendo que la plataforma se quede sin administradores activos. 


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

### Clases
![](docs/Diagrama%20de%20Clases%20UML/Diagrama%20de%20clases.jpeg)

### Entidad-Relación
![](docs/Diagrama%20Entidad-Relación/gestion_inventario_ER.png)
