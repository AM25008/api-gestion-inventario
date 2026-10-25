package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.request.ProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorResponseDTO toDTO(Proveedor proveedor) {
        if (proveedor == null) return null;

        return ProveedorResponseDTO.builder()
                .idProveedor(proveedor.getIdProveedor())
                .nombreProveedor(proveedor.getNombreProveedor())
                .correo(proveedor.getCorreo())
                .telefono(proveedor.getTelefono())
                .direccion(proveedor.getDireccion())
                .build();
    }

    public Proveedor toEntity(ProveedorRequestDTO dto) {
        if (dto == null) return null;

        return Proveedor.builder()
                .nombreProveedor(dto.getNombreProveedor())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .activo(true)
                .build();
    }
}