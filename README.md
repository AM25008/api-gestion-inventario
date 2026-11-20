# API Gestión de Inventarios
[![Java](https://img.shields.io/badge/Java-ED8B00.svg?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F.svg?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED.svg?logo=docker&logoColor=white)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5.svg?logo=kubernetes&logoColor=white)](https://kubernetes.io/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D.svg?logo=swagger&logoColor=black)](https://swagger.io/)
[![Actions](https://img.shields.io/badge/GitHubActions-2088FF.svg?logo=githubactions&logoColor=white)](https://github.com/features/actions)
[![Contributors](https://img.shields.io/github/contributors/am25008/api-gestion-inventario.svg)](https://github.com/am25008/api-gestion-inventario/graphs/contributors)

> Proyecto Final de Programación Orientada a Objetos - Grupo 7

API REST desarrollada con Spring Boot para administrar el catálogo de productos de un negocio, sus categorías y proveedores, y registra cada entrada y salida
de stock, actualizando automáticamente la cantidad disponible de cada producto.

## Equipo de Desarrollo
| Nombre                                | Carnet  | Usuario                                      |
|---------------------------------------|---------|----------------------------------------------|
| Carlos Alfredo Ayala Mejía | AM25008 | [@AM25008](https://github.com/AM25008)|
| Katherine Jeanmillette Santos Sermeño | SS25031 | [@SS25031](https://github.com/SS25031)       |
| Audiel Isaac Terán Morales | TM25020 | [@Audiel007](https://github.com/Audiel007)   |
| Denilson Alfredo Vega Granadino | VG20024 | [@DenilsonVG](https://github.com/DenilsonVG) |
| Jefferson Gerardo Quezada López| QL25003 | [@ql25003-ux](https://github.com/ql25003-ux)    |

## Descripción del Proyecto
El sistema permite llevar el control del inventario de una organización:
- Mantener un catálogo de **productos**, organizados por **categoría**.
- Registrar los **proveedores** y qué productos suministra cada uno, con el precio de compra de cada combinación.
- Registrar **movimientos de inventario** (entradas y salidas), con trazabilidad de qué usuario los realizó y cuándo.
- Detectar productos con **stock bajo** para anticipar reposiciones.

## Funcionalidades
- CRUD de categorías, proveedores, productos y usuarios.
- Asociación N:M entre productos y proveedores, con precio de compra por combinación.
- Registro de entradas y salidas de stock, con validación de stock suficiente.
- Consulta del historial de movimientos por producto, por usuario o por tipo.
- Consulta de productos con stock bajo y cálculo del nivel de stock (`SIN_STOCK`, `BAJO`, `NORMAL`, `ALTO`).
- Eliminación lógica (soft delete): los registros con historial nunca se borran físicamente.
- Validación de datos de entrada y manejo centralizado de errores, con respuestas de error consistentes.
- Control de permisos por rol (Administrador / Operador).

## Roles y Permisos
| Recurso | Administrador | Operador |
|---|---|---|
| Categorías | Consultar, crear, actualizar, eliminar | Consultar |
| Proveedores | Consultar, crear, actualizar, eliminar | Consultar |
| Productos | Consultar, crear, actualizar, eliminar | Consultar, crear, actualizar |
| Asociación producto-proveedor | Consultar, crear, actualizar, eliminar | Consultar, crear, actualizar |
| Usuarios | Todo | Sin acceso |
| Movimientos de inventario | Registrar y consultar | Registrar y consultar |

## Estructura del proyecto
```
api-gestion-inventario/
├── docs/                              # Documentación adicional (diagramas, casos de uso)
│   ├── Casos de Uso/
│   ├── Diagrama de Clases UML/
│   ├── Diagrama Entidad-Relación/
│   ├── Evidencias/                    # Capturas del despliegue y del pipeline
│   └── Postman/                       # Colección para probar los endpoints
│
├── src/
│   ├── main/
│   │   ├── java/com/grupo7/GestionInventarioAPI/
│   │   │   ├── controller/            # Endpoints REST
│   │   │   ├── service/               # Lógica de negocio (interfaces)
│   │   │   │   └── impl/              # Implementaciones de los services
│   │   │   ├── repository/            # Acceso a datos (Spring Data JPA)
│   │   │   ├── model/                 # Entidades JPA
│   │   │   │   └── enums/             # RolUsuario, TipoMovimiento, NivelStock
│   │   │   ├── dto/
│   │   │   │   ├── request/           # Datos que recibe la API (con validaciones)
│   │   │   │   └── response/          # Datos que devuelve la API
│   │   │   ├── mapper/                # Conversión entre Entity y DTO
│   │   │   └── exception/             # Excepciones personalizadas y manejador global
│   │   └── resources/
│   │       ├── db/migration/          # Scripts SQL de Flyway
│   │       └── application.properties
│   └── test/                          # Pruebas unitarias
│
├── .github/
│   └── workflows/ci.yml               # Pipeline de integración continua
|
├── k8s/                               # Manifiestos para Kubernetes
│   ├── 01-postgres-secret.yaml
│   ├── 02-postgres-service.yaml
│   ├── 03-postgres-deployment.yaml
│   ├── 04-deployment.yaml
│   └── 05-service.yaml
├── Dockerfile                         # Construcción de la imagen de la API
├── docker-compose.yml                 # API y base de datos con Docker Compose
├── Deploy.md                          # Guía de despliegue en Kubernetes y uso de act
├── pom.xml                            # Gestión de dependencias
├── .dockerignore                      # Archivos excluidos de la imagen Docker
├── .gitignore                         # Archivos y directorios ignorados por Git
└── README.md                          # Documentación principal
```

## Modelo de Datos

### Diagrama Entidad-Relación

![](docs/Diagrama%20Entidad-Relación/DER-docs.png)

### Diagrama de Clases

![](docs/Diagrama%20de%20Clases%20UML/diagrama-clases-docs.png)

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

## Lógica del Negocio
- El stock de un producto solo cambia mediante movimientos de inventario.
- Una **salida** se rechaza si la cantidad supera el stock disponible; el stock nunca queda negativo.
- Registrar un movimiento: se guarda el movimiento y se actualiza el stock.
- Eliminar un producto, proveedor, categoría o usuario es una **baja lógica** (`activo = false`). La única excepción es la asociación producto-proveedor, que se elimina físicamente porque nada depende de ella.
- No pueden existir dos categorías activas con el mismo nombre, ni dos proveedores con el mismo correo.
- Un administrador no puede desactivar su propia cuenta.

## Documentación de la API
URL base: `http://localhost:8080/api`

El parámetro `user` es el id del usuario que ejecuta la operación. Es obligatorio en las operaciones que modifican datos y en todo lo que sea exclusivo de administrador. Las consultas abiertas a ambos roles no lo requieren.

### Categorías - `/api/categorias`
| Método | Ruta | Descripción | Permiso |
|---|---|---|---|
| GET | `/api/categorias` | Listar categorías activas | Ambos |
| GET | `/api/categorias/{id}` | Obtener una categoría | Ambos |
| POST | `/api/categorias?user=` | Crear categoría | Administrador |
| PUT | `/api/categorias/{id}?user=` | Actualizar categoría | Administrador |
| DELETE | `/api/categorias/{id}?user=` | Dar de baja una categoría | Administrador |

### Proveedores - `/api/proveedores`
| Método | Ruta | Descripción | Permiso |
|---|---|---|---|
| GET | `/api/proveedores` | Listar proveedores activos | Ambos |
| GET | `/api/proveedores/{id}` | Obtener un proveedor | Ambos |
| POST | `/api/proveedores?user=` | Crear proveedor | Administrador |
| PUT | `/api/proveedores/{id}?user=` | Actualizar proveedor | Administrador |
| DELETE | `/api/proveedores/{id}?user=` | Dar de baja un proveedor | Administrador |

### Productos - `/api/productos`
| Método | Ruta | Descripción | Permiso |
|---|---|---|---|
| GET | `/api/productos` | Listar productos activos | Ambos |
| GET | `/api/productos/{id}` | Obtener un producto | Ambos |
| GET | `/api/productos/stock-bajo` | Productos con stock menor o igual al mínimo | Ambos |
| GET | `/api/productos/valor-inventario` | Calcular el valor total del inventario activo y consultar la cantidad de productos y unidades | Ambos |
| POST | `/api/productos?user=` | Crear producto | Ambos |
| PUT | `/api/productos/{id}?user=` | Actualizar producto (no modifica el stock) | Ambos |
| DELETE | `/api/productos/{id}?user=` | Dar de baja un producto | Administrador |

La respuesta de `/api/productos/valor-inventario` contiene el valor total del inventario, la cantidad de productos activos y las unidades totales. Por ejemplo:
```json
{
  "valorTotal": 1250.50,
  "cantidadProductos": 8,
  "unidadesTotales": 42
}
```

### Asociación producto-proveedor - `/api/producto-proveedor`
| Método | Ruta | Descripción | Permiso |
|---|---|---|---|
| GET | `/api/producto-proveedor` | Listar todas las asociaciones | Ambos |
| GET | `/api/producto-proveedor/{id}` | Obtener una asociación | Ambos |
| GET | `/api/producto-proveedor/producto/{idProducto}` | Proveedores de un producto | Ambos |
| GET | `/api/producto-proveedor/proveedor/{idProveedor}` | Productos de un proveedor | Ambos |
| POST | `/api/producto-proveedor?user=` | Asociar un producto con un proveedor | Ambos |
| PUT | `/api/producto-proveedor/{id}?user=` | Actualizar el precio de compra | Ambos |
| DELETE | `/api/producto-proveedor/{id}?user=` | Eliminar la asociación | Administrador |

### Movimientos de inventario - `/api/movimientos`
| Método | Ruta | Descripción | Permiso |
|---|---|---|---|
| POST | `/api/movimientos?user=` | Registrar una entrada o salida | Ambos |
| GET | `/api/movimientos` | Listar todos los movimientos | Ambos |
| GET | `/api/movimientos/{id}` | Obtener un movimiento | Ambos |
| GET | `/api/movimientos/producto/{idProducto}` | Historial de un producto | Ambos |
| GET | `/api/movimientos/usuario/{idUsuario}` | Movimientos de un usuario | Ambos |
| GET | `/api/movimientos/tipoMovimiento/{tipo}` | Movimientos por tipo (`ENTRADA` / `SALIDA`) | Ambos |
> Los movimientos son un registro histórico: no se editan ni se eliminan.

### Usuarios - `/api/usuarios`

> Todas las operaciones son exclusivas del administrador, incluidas las consultas, y requieren `?user=`.

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/usuarios` | Listar usuarios activos |
| GET | `/api/usuarios/{id}` | Obtener un usuario |
| POST | `/api/usuarios` | Crear usuario |
| PUT | `/api/usuarios/{id}` | Actualizar usuario |
| DELETE | `/api/usuarios/{id}` | Desactivar usuario |

### Swagger / OpenAPI
La documentación interactiva de los endpoints está disponible en el siguiente enlace (la aplicación se debe estar ejecutando):
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Especificación OpenAPI: `http://localhost:8080/v3/api-docs`

## Instalación y Configuración
### Requisitos
- Docker Desktop con Docker Compose, o Docker Engine con el complemento Docker Compose

> Docker es el único requisito para ejecutar la API y la base de datos en contenedores. Para ejecutar la API directamente con Java, también se necesita Java 21.

### 1. Clonar el repositorio
```bash
git clone https://github.com/AM25008/api-gestion-inventario.git
cd api-gestion-inventario
```

### 2. Ejecutar la aplicación con Docker
```bash
docker compose up -d --build
```

Este comando construye la imagen de la API e inicia los contenedores de la API y PostgreSQL. La API queda disponible en `http://localhost:8080/api`.

La configuración de PostgreSQL es:
| Dato | Valor |
|---|---|
| Host | `localhost` |
| Puerto | `5433` |
| Base de datos | `db_inventario` |
| Usuario | `postgres` |
| Contraseña | `proyecto_poo` |

>[!NOTE]
> - Para visualizar la base de datos con pgAdmin, DBeaver u otra herramienta, se puede registrar una conexión con las credenciales anteriores.
> - Para detener los contenedores, ejecutar `docker compose stop`.
> - Para eliminar los contenedores (y el volúmen), ejecurar `docker compose down -v`

### 3. Ejecutar la aplicación con Java
Esta alternativa requiere Docker para iniciar PostgreSQL y Java 21 para ejecutar Spring Boot.

Primero, iniciar únicamente la base de datos:
```bash
docker compose up -d database
```

Luego, ejecutar la API desde la raíz del proyecto. En Windows:
```powershell
.\mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8080/api`

### 4. Migraciones de la base de datos (Flyway)
Flyway aplica automáticamente las migraciones de `src/main/resources/db/migration/`, que crean las tablas y algunos datos iniciales de ejemplo al iniciar la aplicación.

### 5. Probar con Postman
En la carpeta `docs/Postman` se encuentra una colección JSON que puede importarse en Postman para probar las peticiones.

## Despliegue en Kubernetes
La guía de despliegue local en Kubernetes con `kind`, así como las instrucciones para ejecutar el pipeline localmente con `act`, se encuentran en [Deploy.md](Deploy.md).

## Stack Tecnológico
| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje |
| Spring Boot 4.1.1 | Framework (Spring Web MVC, Spring Data JPA) |
| PostgreSQL 16 | Base de datos |
| Flyway | Migraciones versionadas del esquema |
| Jakarta Validation | Validación de DTOs |
| Springdoc OpenAPI | Documentación interactiva de la API con Swagger UI |
| Lombok | Utilidades |
| Docker Compose | Ejecución de la API y PostgreSQL en contenedores |
| Maven | Gestión de dependencias |

## Licencia
Proyecto académico - Universidad de El Salvador, Facultad Multidisciplinaria de Occidente.





