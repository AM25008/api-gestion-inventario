package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProductoProveedorMapper;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.model.ProductoProveedor;
import com.grupo7.GestionInventarioAPI.model.Proveedor;
import com.grupo7.GestionInventarioAPI.repository.ProductoProveedorRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
import com.grupo7.GestionInventarioAPI.repository.ProveedorRepository;
import com.grupo7.GestionInventarioAPI.service.Helper;
import com.grupo7.GestionInventarioAPI.service.ProductoProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoProveedorServiceImpl implements ProductoProveedorService {

    private final ProductoProveedorRepository repository;
    private final ProductoProveedorMapper mapper;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final Helper helper;


    @Override
    @Transactional(readOnly = true)
    public List<ProductoProveedorResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoProveedorResponseDTO obtenerPorId(Integer id) {
        ProductoProveedor prodProv = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro una asociacion con id " + id));

        return mapper.toDTO(prodProv);
    }

    @Override
    @Transactional
    public ProductoProveedorResponseDTO crearProductoProveedor(ProductoProveedorRequestDTO dto) {
        // Validar que el producto exista
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(dto.getIdProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro un producto con id " + dto.getIdProducto()));

        // Validar que el proveedor exista
        Proveedor proveedor = proveedorRepository.findByIdProveedorAndActivoTrue(dto.getIdProveedor())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro un proveedor con id " + dto.getIdProducto()));

        // duplicados
        if(repository.existsByProducto_IdProductoAndProveedor_IdProveedor(dto.getIdProducto(), dto.getIdProveedor())){
            throw new RecursoDuplicadoException(
                    "El producto con id " + dto.getIdProducto()
                            + " ya está asociado al proveedor con id "
                            + dto.getIdProveedor()
            );
        }

        ProductoProveedor productoProveedor = ProductoProveedor.builder()
                .proveedor(proveedor)
                .producto(producto)
                .precioCompra(dto.getPrecioCompra())
                .build();

        repository.save(productoProveedor);
        return mapper.toDTO(productoProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoProveedorResponseDTO> obtenerProductosPorProveedor(Integer idProveedor) {
        // Verificar que el proveedor exista
        Proveedor proveedor = proveedorRepository.findByIdProveedorAndActivoTrue(idProveedor)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro un proveedor con id " + idProveedor));

        List<ProductoProveedor> productos = repository.findByProveedor_IdProveedor(idProveedor);

        return productos.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoProveedorResponseDTO> obtenerProveedoresPorProducto(Integer idProducto) {
        // Verificar que el producto exista
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro un producto con id " + idProducto));

        return repository.findByProducto_IdProducto(idProducto).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ProductoProveedorResponseDTO actualizar(Integer idProductoProveedor, ProductoProveedorUpdateDTO dto) {
        ProductoProveedor productoProveedor = repository.findById(idProductoProveedor)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro una asociacion con id " + idProductoProveedor));

        if(dto.getPrecioCompra() != null){
            productoProveedor.setPrecioCompra(dto.getPrecioCompra());
        }

        ProductoProveedor actualizado = repository.save(productoProveedor);
        return mapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer idUsuario, Integer idProductoProveedor) {
        helper.exigirAdmin(idUsuario);

        if(!repository.existsById(idProductoProveedor)){
            throw new RecursoDuplicadoException("No se encontro una asociacion con id " + idProductoProveedor);
        }

        repository.deleteById(idProductoProveedor);


    }
}
