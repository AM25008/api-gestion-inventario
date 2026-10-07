package com.grupo7.GestionInventarioAPI.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductoProveedorResponseDTO {

    private Integer idProductoProveedor;

    private String producto;

    private String proveedor;

    private BigDecimal precioCompra;
}
