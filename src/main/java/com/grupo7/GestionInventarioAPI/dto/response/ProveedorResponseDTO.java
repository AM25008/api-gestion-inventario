package com.grupo7.GestionInventarioAPI.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProveedorResponseDTO {

    private Integer idProveedor;

    private String nombreProveedor;

    private String correo;

    private String telefono;

    private String direccion;
}