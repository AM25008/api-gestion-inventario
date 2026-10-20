package com.grupo7.GestionInventarioAPI.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResponseDTO {
    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcion;
}