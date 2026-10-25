package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;

import java.util.List;

public interface ProductoProveedorService {

    List<ProductoProveedorResponseDTO> obtenerTodos();

    ProductoProveedorResponseDTO obtenerPorId(Integer id);

    ProductoProveedorResponseDTO crearProductoProveedor(ProductoProveedorRequestDTO dto);

    List<ProductoProveedorResponseDTO> obtenerProductosPorProveedor(Integer idProveedor);

    List<ProductoProveedorResponseDTO> obtenerProveedoresPorProducto(Integer idProducto);

    ProductoProveedorResponseDTO actualizar(Integer idProductoProveedor, ProductoProveedorUpdateDTO dto);

    void eliminar(Integer idUsuario, Integer idProductoProveedor);
}
