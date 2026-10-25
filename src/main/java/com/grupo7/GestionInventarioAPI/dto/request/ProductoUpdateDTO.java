package com.grupo7.GestionInventarioAPI.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductoUpdateDTO {

    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombreProducto;

    private Integer idCategoria;

    @Size(max = 200, message = "La descripción no puede exceder los 200 caracteres.")
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo.")
    private BigDecimal precio;

    @PositiveOrZero(message = "El stock mínimo  debe ser positivo o cero.")
    private Integer stockMinimo;
}