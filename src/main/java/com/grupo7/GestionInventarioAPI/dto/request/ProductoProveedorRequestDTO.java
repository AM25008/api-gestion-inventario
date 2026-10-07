package com.grupo7.GestionInventarioAPI.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class ProductoProveedorRequestDTO {

    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor a 0")
    private Integer idProducto;

    @NotNull(message = "El proveedor es obligatorio")
    @Positive(message = "El id del proveedor debe ser mayor a 0")
    private Integer idProveedor;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio del producto debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal precioCompra;
}
