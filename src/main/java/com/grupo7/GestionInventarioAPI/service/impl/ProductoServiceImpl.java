package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProductoMapper;
import com.grupo7.GestionInventarioAPI.model.*;
import com.grupo7.GestionInventarioAPI.repository.CategoriaRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoProveedorRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
import com.grupo7.GestionInventarioAPI.service.Helper;
import com.grupo7.GestionInventarioAPI.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;
    private final Helper helper;
    private final ProductoProveedorRepository productoProveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado o inactivo con ID: " + id));
        return convertirAResponse(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.getIdCategoria()));

        if (!categoria.isActiva()) {
            throw new RecursoNoEncontradoException("La categoria con ID " + dto.getIdCategoria() + " está inactiva.");
        }

        if (productoRepository.existsByNombreProducto(dto.getNombreProducto())) {
            throw new RecursoDuplicadoException("Ya existe un producto con el nombre: " + dto.getNombreProducto());
        }

        Producto producto = productoMapper.toEntity(dto, categoria);
        producto.setActivo(true);

        Producto guardado = productoRepository.save(producto);
        return convertirAResponse(guardado);
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Integer id, ProductoUpdateDTO dto) {
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado o inactivo con ID: " + id));

        if (dto.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
            if (!categoria.isActiva()) {
                throw new RecursoNoEncontradoException("La categoría " + dto.getIdCategoria() + " no está activa.");
            }
            producto.setCategoria(categoria);
        }

        productoMapper.updateEntityFromDTO(dto, producto);

        Producto actualizado = productoRepository.save(producto);
        return convertirAResponse(actualizado);
    }

    // aqui recibe el usuario y lo valida con el helper
    @Override
    @Transactional
    public void eliminar(Integer id, Integer idUser) {
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado o inactivo con ID: " + id));

        Usuario usuario = helper.exigirAdmin(idUser);

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> consultarStockBajo() {
        return productoRepository.findProductosConStockBajo()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private List<String> obtenerProveedoresDeProducto(Integer idProducto){

        List<ProductoProveedor> relaciones = productoProveedorRepository.findByProducto_IdProducto(idProducto);

        return relaciones.stream()
                .map(ProductoProveedor::getProveedor)
                .filter(Proveedor::isActivo)
                .map(Proveedor::getNombreProveedor)
                .toList();
    }

    private ProductoResponseDTO convertirAResponse(Producto producto) {
        List<String> proveedores = obtenerProveedoresDeProducto(producto.getIdProducto());

        return productoMapper.toDTO(producto, proveedores);
    }
}