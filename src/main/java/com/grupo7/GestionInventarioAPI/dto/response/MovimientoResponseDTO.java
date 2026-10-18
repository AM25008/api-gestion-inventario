package com.grupo7.GestionInventarioAPI.dto.response;

import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MovimientoResponseDTO {

    private Integer idMovimiento;

    private String producto;

    private String usuario;

    private TipoMovimiento tipoMovimiento;

    private LocalDateTime fechaHora;

    private Integer cantidad;

    private String motivo;
}
