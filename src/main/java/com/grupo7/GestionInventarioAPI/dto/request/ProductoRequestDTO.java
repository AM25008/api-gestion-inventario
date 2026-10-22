package com.grupo7.GestionInventarioAPI.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombreProducto;

    @NotNull(message = "El ID de la categoria es obligatorio.")
    private Integer idCategoria;

    @Size(max = 200, message = "La descripción no puede exceder los 200 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo.")
    private BigDecimal precio;

    @NotNull(message = "El Stock inicial es obligatorio.")
    @PositiveOrZero(message = "El stock debe ser positivo o cero.")
    private Integer stock;

    @NotNull(message = "El stock mínimo es obligatorio.")
    @PositiveOrZero(message = "El stock mínimo debe ser positivo o cero.")
    private Integer stockMinimo;
}
