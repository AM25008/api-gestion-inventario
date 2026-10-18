package com.grupo7.GestionInventarioAPI.dto.response;

import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private RolUsuario rol;
}