package com.grupo7.GestionInventarioAPI.dto.request;

import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MovimientoRequestDTO {

    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El id del producto debe ser positivo")
    private Integer idProducto;

    @NotNull(message = "El usuario es obligatorio")
    @Positive(message = "El id del usuario debe ser positivo")
    private Integer idUsuario;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimiento tipoMovimiento;

    @NotNull(message = "La cantidad de productos es obligatoria")
    @Min(value = 1, message = "El stock debe ser mayor a 0")
    private Integer cantidad;

    @Size(max = 200, message = "El motivo no debe exceder los 200 caracteres")
    private String motivo;
}
