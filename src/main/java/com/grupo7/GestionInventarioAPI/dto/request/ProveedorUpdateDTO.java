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

    private String telefono;

    private String direccion;
}