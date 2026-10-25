package com.grupo7.GestionInventarioAPI.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class ProductoProveedorUpdateDTO {

    @DecimalMin(value = "0.0", message = "El precio del producto debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal precioCompra;
}
