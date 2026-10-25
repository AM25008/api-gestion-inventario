package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.request.UsuarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    /**
     * Convierte de Entidad a DTO de Respuesta.
     */
    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) return null;

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombreUsuario(entity.getNombreUsuario());
        dto.setApellidoUsuario(entity.getApellidoUsuario());
        dto.setRol(entity.getRolUsuario()); // Mapea el enum RolUsuario a la propiedad rol del DTO
        return dto;
    }

    /**
     * Convierte de DTO de Requerimiento a Entidad Base.
     */
    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setApellidoUsuario(dto.getApellidoUsuario());
        usuario.setRolUsuario(dto.getRol()); // Mapea el enum RolUsuario recibido
        usuario.setActivo(true); // REQUISITO: Todo usuario creado inicia activo por defecto
        return usuario;
    }
}