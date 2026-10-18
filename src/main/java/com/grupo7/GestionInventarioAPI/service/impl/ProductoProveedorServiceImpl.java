package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProductoProveedorMapper;
import com.grupo7.GestionInventarioAPI.model.ProductoProveedor;
import com.grupo7.GestionInventarioAPI.repository.ProductoProveedorRepository;
import com.grupo7.GestionInventarioAPI.service.ProductoProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoProveedorServiceImpl implements ProductoProveedorService {

    private final ProductoProveedorRepository repository;
    private final ProductoProveedorMapper mapper;

    @Override
    public List<ProductoProveedorResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public ProductoProveedorResponseDTO obtenerPorId(Integer id) {
        ProductoProveedor prodProv = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro una asociacion con id " + id));

        return mapper.toDTO(prodProv);
    }

    @Override
    public ProductoProveedorResponseDTO crearProductoProveedor(ProductoProveedorRequestDTO dto) {
        return null;
    }

    @Override
    public ProductoProveedorResponseDTO obtenerProductosPorProveedor(Integer idProveedor) {
        return null;
    }

    @Override
    public ProductoProveedorResponseDTO obtenerProveedoresPorProducto(Integer idProducto) {
        return null;
    }

    @Override
    public ProductoProveedorResponseDTO actualizar(Integer idProductoProveedor, ProductoProveedorUpdateDTO dto) {
        return null;
    }

    @Override
    public void eliminar(Integer idUsuario, Integer idProductoProveedor) {

    }
}
