package com.grupo7.GestionInventarioAPI.service;


import com.grupo7.GestionInventarioAPI.dto.request.MovimientoInventarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;

import java.util.List;

public interface MovimientoInventarioService {

    List<MovimientoInventarioResponseDTO> obtenerTodos();

    MovimientoInventarioResponseDTO obtenerPorId(Integer id);

    MovimientoInventarioResponseDTO registrarMovimiento(Integer idUsuario, MovimientoInventarioRequestDTO dto);

    List<MovimientoInventarioResponseDTO> obtenerMovimientosPorProducto(Integer idProducto);

    List<MovimientoInventarioResponseDTO> obtenerMovimientosPorUsuario(Integer idUsuario);

    List<MovimientoInventarioResponseDTO> obtenerMovimientosPorTipo(TipoMovimiento tipo);

}
