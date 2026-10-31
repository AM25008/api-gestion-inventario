CREATE INDEX IF NOT EXISTS idx_producto_proveedor_id_producto
    ON producto_proveedor (id_producto);

CREATE INDEX IF NOT EXISTS idx_producto_proveedor_id_proveedor
    ON producto_proveedor (id_proveedor);
