package com.grupo7.GestionInventarioAPI.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@JsonPropertyOrder({
        "idProductoProveedor",
        "producto",
        "proveedor",
        "precioCompra"
})
public class ProductoProveedorResponseDTO {

    private Integer idProductoProveedor;

    private String producto;

    private String proveedor;

    private BigDecimal precioCompra;
}
