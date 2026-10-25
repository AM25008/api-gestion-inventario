package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.ProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;

import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseDTO> listarActivos();

    ProveedorResponseDTO obtenerPorId(Integer id);

    ProveedorResponseDTO crear(ProveedorRequestDTO dto, Integer usuarioId);

    ProveedorResponseDTO actualizar(
            Integer id,
            ProveedorUpdateDTO dto,
            Integer usuarioId
    );

    void eliminar(Integer id, Integer usuarioId);
}