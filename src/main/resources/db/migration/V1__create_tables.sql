-- Script de creacion de tablas
-- Tabla categorias
CREATE TABLE categorias(
        id_categoria SERIAL PRIMARY KEY,
        nombre_categoria VARCHAR(150) NOT NULL,
        descripcion VARCHAR(200),
        activa BOOLEAN DEFAULT TRUE
);

-- Tabla usuarios
CREATE TABLE usuarios(
        id_usuario SERIAL PRIMARY KEY,
        nombre_usuario VARCHAR(150) NOT NULL,
        apellido_usuario VARCHAR(150) NOT NULL,
        rol VARCHAR(20) NOT NULL CHECK(rol IN ('ADMIN', 'OPERADOR')),
        activo BOOLEAN DEFAULT TRUE
);

-- Tabla proveedores
CREATE TABLE proveedores(
        id_proveedor SERIAL PRIMARY KEY,
        nombre_proveedor VARCHAR(150) NOT NULL,
        correo VARCHAR(100) NOT NULL UNIQUE,
        telefono VARCHAR(9) NOT NULL,
        direccion VARCHAR(150) NOT NULL,
        activo BOOLEAN DEFAULT TRUE
);

-- Tabla productos
CREATE TABLE productos(
        id_producto SERIAL PRIMARY KEY,
        id_categoria INTEGER NOT NULL REFERENCES categorias(id_categoria),
        nombre_producto VARCHAR(100) NOT NULL,
        descripcion VARCHAR(200),
        precio DECIMAL(10,2) NOT NULL CHECK(precio >= 0),
        stock_actual INTEGER NOT NULL CHECK(stock_actual >= 0) DEFAULT 0,
        stock_minimo INTEGER NOT NULL CHECK(stock_minimo >= 0) DEFAULT 4,
        activo BOOLEAN DEFAULT TRUE
);

-- Tabla producto_proveedor
CREATE TABLE producto_proveedor(
        id_producto_proveedor SERIAL PRIMARY KEY,
        id_producto INTEGER NOT NULL REFERENCES productos(id_producto),
        id_proveedor INTEGER NOT NULL REFERENCES proveedores(id_proveedor),
        precio_compra DECIMAL(10,2) NOT NULL CHECK(precio_compra >= 0)
);

-- Tabla movimientos
CREATE TABLE movimientos(
        id_movimiento SERIAL PRIMARY KEY,
        id_producto INTEGER NOT NULL REFERENCES productos(id_producto),
        id_usuario INTEGER NOT NULL REFERENCES usuarios(id_usuario),
        tipo_movimiento VARCHAR(20) NOT NULL CHECK(tipo_movimiento IN ('ENTRADA', 'SALIDA')),
        fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        cantidad INTEGER NOT NULL CHECK(cantidad > 0),
        precio_unitario DECIMAL(10,2) CHECK(precio_unitario >= 0),
		motivo VARCHAR(200)
);