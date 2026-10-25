package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.UsuarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.UsuarioUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> obtenerTodos(Integer idUsuarioEmisor);
    UsuarioResponseDTO obtenerPorId(Integer id, Integer idUsuarioEmisor);
    UsuarioResponseDTO crear(UsuarioRequestDTO dto, Integer idUsuarioEmisor);
    UsuarioResponseDTO actualizar(Integer id, UsuarioUpdateDTO dto, Integer idUsuarioEmisor);
    void eliminar(Integer id, Integer idUsuarioEmisor);
}