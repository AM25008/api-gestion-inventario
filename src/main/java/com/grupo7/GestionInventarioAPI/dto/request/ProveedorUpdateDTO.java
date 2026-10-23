package com.grupo7.GestionInventarioAPI.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorUpdateDTO {

    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombreProveedor;

    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @Size(max = 9, message = "El telefono no puede exceder los 9 caracteres")
    private String telefono;

    @Size(max = 150, message = "La dirección no puede superar los 150 caracteres")
    private String direccion;
}