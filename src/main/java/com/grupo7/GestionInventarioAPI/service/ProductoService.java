package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    List<ProductoResponseDTO> listarActivos();

    ProductoResponseDTO obtenerPorId(Integer id);

    ProductoResponseDTO crear(ProductoRequestDTO dto);

    ProductoResponseDTO actualizar(Integer id, ProductoUpdateDTO dto);

    // recibe id de usuario y lo valida en la impl
    void eliminar(Integer idProducto, Integer idUsuario);

    List<ProductoResponseDTO> consultarStockBajo();
}
