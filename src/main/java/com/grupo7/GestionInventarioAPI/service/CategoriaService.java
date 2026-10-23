package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponseDTO> listarActivas();
    CategoriaResponseDTO obtenerPorId(Integer id);
    CategoriaResponseDTO crear(CategoriaRequestDTO dto, Integer usuarioId);
    CategoriaResponseDTO actualizar(Integer id, CategoriaUpdateDTO dto, Integer usuarioId);
    void eliminarLogico(Integer id, Integer usuarioId);
}
