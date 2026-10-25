package com.grupo7.GestionInventarioAPI.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonPropertyOrder({
        "idMovimiento",
        "producto",
        "usuario",
        "tipoMovimiento",
        "fechaHora",
        "cantidad",
        "motivo"
})
public class MovimientoInventarioResponseDTO {

    private Integer idMovimiento;

    private String producto;

    private String usuario;

    private TipoMovimiento tipoMovimiento;

    private LocalDateTime fechaHora;

    private Integer cantidad;

    private String motivo;
}
