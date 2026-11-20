package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProductoMapper;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.repository.CategoriaRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
import com.grupo7.GestionInventarioAPI.service.Helper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private ProductoMapper productoMapper;
    @Mock
    private Helper helper;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void obtenerPorId_CuandoExiste_RetornaProducto() {

        Integer id = 1;
        Producto productoMock = new Producto();
        productoMock.setIdProducto(id);
        productoMock.setNombreProducto("Laptop");

        ProductoResponseDTO responseMock = new ProductoResponseDTO();
        responseMock.setNombreProducto("Laptop");

        when(productoRepository.findByIdProductoAndActivoTrue(id)).thenReturn(Optional.of(productoMock));
        when(productoMapper.toDTO(productoMock)).thenReturn(responseMock);


        ProductoResponseDTO resultado = productoService.obtenerPorId(id);


        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombreProducto());
        verify(productoRepository, times(1)).findByIdProductoAndActivoTrue(id);
    }

    @Test
    void obtenerPorId_CuandoNoExiste_LanzaExcepcion() {

        Integer id = 99;
        when(productoRepository.findByIdProductoAndActivoTrue(id)).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> {
            productoService.obtenerPorId(id);
        });
    }

    @Test
    void crear_CuandoCategoriaExisteYNombreEsUnico_GuardaProducto() {

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setIdCategoria(1);
        request.setNombreProducto("Teclado");
        request.setPrecio(new BigDecimal("25.50"));

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setActiva(true);

        Producto productoEntity = new Producto();
        Producto guardado = new Producto();
        guardado.setIdProducto(1);

        ProductoResponseDTO response = new ProductoResponseDTO();
        response.setNombreProducto("Teclado");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(productoRepository.existsByNombreProducto("Teclado")).thenReturn(false);
        when(productoMapper.toEntity(request, categoria)).thenReturn(productoEntity);
        when(productoRepository.save(any(Producto.class))).thenReturn(guardado);
        when(productoMapper.toDTO(guardado)).thenReturn(response);


        ProductoResponseDTO resultado = productoService.crear(request);


        assertNotNull(resultado);
        assertEquals("Teclado", resultado.getNombreProducto());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }
}
