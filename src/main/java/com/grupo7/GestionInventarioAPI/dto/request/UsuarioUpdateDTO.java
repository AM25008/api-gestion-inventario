package com.grupo7.GestionInventarioAPI.dto.request;

import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    @Size(max = 150, message = "El nombre de usuario debe tener como máximo 150 caracteres")
    private String nombreUsuario;

    @Size(max = 150, message = "El apellido de usuario debe tener como máximo 150 caracteres")
    private String apellidoUsuario;

    private RolUsuario rol;
}
