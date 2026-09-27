# API de Gestión Inventario
> Proyecto Final de Programación Orientada a Objetos - Grupo 7

## Descripción
La API de Gestión de Inventario es un servicio backend diseñado para administrar de manera eficiente el flujo de productos, el catálogo de inventario, proveedores y usuarios dentro de una organización. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los recursos principales y ofrece un sistema de auditoría y trazabilidad mediante el registro de movimientos de entrada y salida de mercancía.

## Integrantes
| Nombre                                | Carnet  | Usuario                                      |
|---------------------------------------|---------|----------------------------------------------|
| Katherine Jeanmillette Santos Sermeño | SS25031 | [@SS25031](https://github.com/SS25031)       |
| Audiel Isaac Terán Morales            | TM25020 | [@Audiel007](https://github.com/Audiel007)   |
| Denilson Alfredo Vega Granadino       | VG20024 | [@DenilsonVG](https://github.com/DenilsonVG) |
| Carlos Alfredo Ayala Mejía            | AM25008 | [@AM25008](https://github.com/AM25008)       |
| Jefferson Gerardo Quezada López       | QL25003 | [@ql25003-ux](https://github.com/ql25003-ux)    |

## Arquitectura del modelo de datos
El sistema estructura el dominio del negocio mediante seis entidades principales: 
- Productos
- Categorías
- Proveedores
- ProductoProveedor(relación N:M)
- Movimientos
- Usuarios.

## Diagrama Entidad-Relación
![](docs/Diagrama%20Entidad-Relación/gestion_inventario_ER.png)

- **Productos** → Almacena cada artículo del inventario(`nombre`, `descripción`, `precio`, `stock`) y se conecta con **Categorías** y **Proveedores**.

- **Categorías** → Clasifica los productos en grupos para organizarlos mejor.

- **Proveedores** → Registra quién suministra los productos(`nombre`, `teléfono`, `dirección`).

- **ProductoProveedor** → Es una tabla intermedia (relación N:M) que vincula **Productos** con **Proveedores** y guarda el precio de compra de cada combinación.

- **Movimientos** → Registra cada `entrada` o `salida` de stock con `fecha`, `cantidad` y `precio unitario`. Es lo que mantiene el inventario actualizado.

- **Usuarios** → Identifica quién realiza cada movimiento (`Admin` u `Operador`).

## Actores del Sistema
- **Administrador (`ADMIN`):** Usuario con acceso total al sistema. Encargado de la gestión de usuarios, proveedores, categorías y catálogo de productos, además de registrar y consultar movimientos e historial de stock.
- **Operador (`OPERADOR`):** Usuario enfocado en la operativa diaria. Registra entradas/salidas de inventario y realiza consultas sobre productos, stock e historial de movimientos. No cuenta con permisos para crear, editar o eliminar entidades base del sistema (usuarios, proveedores, categorías o productos).


## Reglas de negocio y lógica de operacion
- **Control de Acceso por Roles:** El sistema identifica al usuario mediante su ID antes de cada acción. El Administrador (`ADMIN`) cuenta con permisos completos de gestión, mientras que el Operador (`OPERADOR`) está enfocado en la operativa diaria y consultas. Si un operador intenta realizar acciones no autorizadas(como eliminar productos o gestionar usuarios), el sistema devuelve un error 403.

- **Preservación de Trazabilidad:** Las entidades principales poseen el atributo `activo`. Si se intenta eliminar físicamente un producto, proveedor o categoría que ya posee movimientos o asociaciones registradas, el sistema rechaza la eliminación de la base de datos y aplica una baja lógica (marcándolo como inactivo) para evitar huérfanos y mantener el historial protegido. 

- **Control de Stock:** Al registrar una `SALIDA`, el sistema valida que la cantidad solicitada no supere el stock actual; de ser así, rechaza la operación con un error de stock insuficiente. Además, incluye un mecanismo de consulta para filtrar productos cuyo stock haya alcanzado o caído por debajo de su stock mínimo.

- **Protección de Administración:** El sistema bloquea los intentos de un administrador por desactivar su propia cuenta, previniendo que la plataforma se quede sin administradores activos. 

## Estructura del proyecto 

```
api-gestion-inventario/
│
├── docs/                               # Diagramas de la Entrega 1
│   ├── Casos de Uso/                   # Diagrama y descripción de cada CU
│   ├── Diagrama de Clases UML/         # Imagen del diagrama de clases
│   └── Diagrama Entidad-Relación/      # Imagen del DER
│
├── src/
│   ├── main/
│   │   ├── java/com/grupo/gestioninventario/
│   │   │   ├── controller/             # Endpoints REST
│   │   │   ├── service/                # Lógica de negocio
│   │   │   ├── repository/             # Interfaces JPA
│   │   │   ├── model/                  # Entidades JPA
│   │   │   │   └── enums/              
│   │   │   ├── dto/                    # DTOs
│   │   │   │   ├── request/            # Datos que recibe la API 
│   │   │   │   └── response/           # Datos que devuelve la API 
│   │   │   ├── mapper/                 # Conversión entre model y DTO
│   │   │   ├── exception/              # Manejo de excepciones
│   │   │   └── config/                 # Configuraciones
│   │   │
│   │   └── resources/
│   │       ├── db.migration/           # Scripts SQL
│   │       └── application.properties  # Conexión con la db
│   │
│   └── test/                           # Pruebas unitarias 
│
├── .gitignore                          # Archivos y directorios ignorados por git
├── pom.xml                             # Gestión de dependencias
└── README.md                           # Documentación principal
```

## Casos de Uso
- **CU-O1: Gestionar Productos**
- **CU-02: Gestionar Proveedores**
- **CU-03: Gestionar Categorías**
- **CU-04: Gestionar Usuarios**
- **CU-05: Registrar Movimiento de Inventario**
- **CU-06: Consultar Historial de Movimientos**
- **CU-07: Consultar Stock Disponible**
- **CU-08: Consultar Stock Bajo**
- **CU-09: Gestionar Asociación Producto-Proveedor**

### Diagrama Casos de uso
![](docs/Casos%20de%20Uso/diagrama_casos_uso2.png)

> La descripción detallada de cada caso de uso puede ser consultada en [Casos de Uso](docs/Casos%20de%20Uso/casos_de_uso.pdf)

## Diagrama de Clases
![](docs/Diagrama%20de%20Clases%20UML/Diagrama%20de%20clases.jpeg)

## Tecnologías a Utilizar

| Tecnología | Uso                                |
|------------|------------------------------------|
| Spring Boot 4.1 | Framework principal                |
| Spring Web | Construcción de la API REST        |
| Spring Data JPA | Persistencia                       |
| PostgreSQL | Base de datos                      |
| Jakarta Validation | Validación de DTOs                 |
| Lombok     | Reducción de código repetitivo     |
| Docker     | Contenerización                    |
| Java 21    | Lenguaje                           |
| Maven      | Gestión de dependencias            |

## Estado del Proyecto
**En desarrollo** — Actualmente el proyecto se encuentra en la fase de planificación y diseño, correspondiente a la primera entrega. Ya se han definido el modelo de datos, casos de uso, reglas de negocio y estructura inicial del proyecto. La siguiente etapa corresponde a la implementación de la API y sus diferentes componentes.