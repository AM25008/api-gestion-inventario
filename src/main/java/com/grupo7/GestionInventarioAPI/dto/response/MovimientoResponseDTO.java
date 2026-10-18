package com.grupo7.GestionInventarioAPI.dto.response;

import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MovimientoResponseDTO {

    private Long idMovimiento;

    private Long producto;

    private Long usuario;

    private TipoMovimiento tipoMovimiento;

    private LocalDateTime fechaHora;

    private Integer cantidad;

    private String motivo;
}
