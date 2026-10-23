package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProductoMapper;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.repository.CategoriaRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
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

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado o inactivo con ID: " + id));
        return productoMapper.toDTO(producto);
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

        Producto producto = productoMapper.toEntity(dto);
        producto.setCategoria(categoria);
        producto.setActivo(true);

        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDTO(guardado);
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
        return productoMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado o inactivo con ID: " + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> consultarStockBajo() {
        return productoRepository.findProductosConStockBajo()
                .stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }
}