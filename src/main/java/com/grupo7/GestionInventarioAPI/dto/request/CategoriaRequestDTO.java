package com.grupo7.GestionInventarioAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoriaRequestDTO {
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 150, message = "El nombre de la categoría debe tener menos de 150 caracteres")
    private String nombreCategoria;

    @Size(max = 200, message = "La descripción de la categoría debe tener menos de 200 caracteres")
    private String descripcion;
}