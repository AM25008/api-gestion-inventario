package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    // Convierte de Entidad a DTO de Respuesta
    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        if (categoria == null) return null;
        
        return CategoriaResponseDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombreCategoria(categoria.getNombreCategoria())
                .descripcion(categoria.getDescripcion())
                .build();
    }

    // Convierte de DTO de Requerimiento a Entidad Base
    public Categoria toEntity(CategoriaRequestDTO dto) {
        if (dto == null) return null;
        
        return Categoria.builder()
                .nombreCategoria(dto.getNombreCategoria())
                .descripcion(dto.getDescripcion())
                .activa(true) // inicia activa por defecto
                .build();
    }
}
