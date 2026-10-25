-- ==========================================
-- DATOS DE PRUEBA: CATEGORIAS
-- ==========================================


INSERT INTO categorias (nombre_categoria, descripcion, activa) VALUES
    ('Computadoras', 'Equipos de computo y portatiles', TRUE),
    ('Perifericos', 'Teclados, ratones y accesorios', TRUE),
    ('Almacenamiento', 'Discos duros y unidades SSD', TRUE),
    ('Redes', 'Equipos y accesorios de red', TRUE),
    ('Accesorios', 'Accesorios tecnologicos variados', TRUE);

-- ==========================================
-- DATOS DE PRUEBA: PROVEEDORES
-- ==========================================

INSERT INTO proveedores (
    nombre_proveedor, correo, telefono, direccion, activo
) VALUES
    ('TecnoSV', 'ventas@tecnosv.com', '2222-1111', 'San Salvador, Centro', TRUE),
    ('CompuMundo', 'contacto@compumundo.com', '2233-2333', 'Santa Tecla, La Libertad', TRUE),
    ('Digital Store', 'ventas@digitalstore.com', '2244-3444', 'Antiguo Cuscatlan, La Libertad', TRUE),
    ('Redes y Mas', 'info@redesymas.com', '2255-4555', 'Soyapango, San Salvador', TRUE),
    ('Accesorios SV', 'pedidos@accesoriossv.com', '2266-5666', 'Mejicanos, San Salvador', TRUE);


-- ==========================================
-- DATOS DE PRUEBA: PRODUCTOS
-- ==========================================

INSERT INTO productos (
    id_categoria, nombre_producto, descripcion,
    precio, stock_actual, stock_minimo, activo
) VALUES
    (1, 'Laptop Lenovo ThinkPad', 'Laptop para trabajo y estudio', 750.00, 20, 4, TRUE),
    (2, 'Teclado mecanico', 'Teclado USB para computadora', 45.50, 3, 4, TRUE),
    (3, 'SSD 1TB', 'Unidad de almacenamiento solido', 85.00, 3, 4, TRUE),
    (4, 'Router WiFi', 'Router inalambrico de doble banda', 60.00, 10, 3, TRUE),
    (5, 'Mouse inalambrico', 'Mouse USB con conexion inalambrica', 18.99, 2, 4, TRUE);


-- ==========================================
-- DATOS DE PRUEBA: PRODUCTO_PROVEEDOR
-- ==========================================

INSERT INTO producto_proveedor (id_producto, id_proveedor, precio_compra) VALUES
    (1, 1, 620.00),
    (2, 2, 32.00),
    (3, 3, 65.00),
    (4, 4, 42.00),
    (5, 5, 12.00);


-- ==========================================
-- DATOS DE PRUEBA: MOVIMIENTOS
-- ==========================================

INSERT INTO movimientos (
    id_producto, id_usuario, tipo_movimiento,
    fecha_hora, cantidad, motivo
) VALUES
    (1, 1, 'ENTRADA', CURRENT_TIMESTAMP, 20, 'Inventario inicial'),
    (2, 1, 'ENTRADA', CURRENT_TIMESTAMP, 15, 'Compra de teclados'),
    (3, 1, 'ENTRADA', CURRENT_TIMESTAMP, 10, 'Compra de unidades SSD'),
    (4, 1, 'SALIDA', CURRENT_TIMESTAMP, 2, 'Entrega a cliente'),
    (5, 1, 'SALIDA', CURRENT_TIMESTAMP, 1, 'Salida de accesorio');