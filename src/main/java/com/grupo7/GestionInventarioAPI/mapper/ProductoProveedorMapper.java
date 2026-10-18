package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.model.ProductoProveedor;
import org.springframework.stereotype.Component;

@Component
public class ProductoProveedorMapper {

    public ProductoProveedorResponseDTO toDTO(ProductoProveedor prodProv){
        if(prodProv == null) return null;

        return ProductoProveedorResponseDTO.builder()
                .idProductoProveedor(prodProv.getIdProductoProveedor())
                .producto(prodProv.getProducto().getNombreProducto())
                .proveedor(prodProv.getProveedor().getNombreProveedor())
                .precioCompra(prodProv.getPrecioCompra())
                .build();
    }
}
