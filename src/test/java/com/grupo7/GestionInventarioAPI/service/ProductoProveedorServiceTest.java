package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.mapper.ProductoProveedorMapper;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.model.ProductoProveedor;
import com.grupo7.GestionInventarioAPI.model.Proveedor;
import com.grupo7.GestionInventarioAPI.repository.ProductoProveedorRepository;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoProveedorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoProveedorServiceTest {

    @Mock
    private ProductoProveedorRepository repository;

    @InjectMocks
    private ProductoProveedorServiceImpl service;

    @Mock
    private ProductoProveedorMapper mapper;

    private ProductoProveedor productoProveedor;
    private ProductoProveedorRequestDTO requestDTO;
    private ProductoProveedorResponseDTO responseDTO;
    private ProductoProveedorUpdateDTO updateDTO;

    private Producto producto;
    private Categoria categoria;
    private Proveedor proveedor;

    @BeforeEach
    void setUp(){
        // categoria de prueba
        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("Perifericos");
        categoria.setDescripcion("Accesorios de computadoras");

        // producto de prueba
        producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombreProducto("Mouse inalambrico");
        producto.setDescripcion("Mouse inalambrico, rgb");
        producto.setPrecio(BigDecimal.valueOf(89.99));
        producto.setCategoria(categoria);

        // proveedor de prueba
        proveedor = new Proveedor();
        proveedor.setIdProveedor(1);
        proveedor.setNombreProveedor("GlobalTrack");
        proveedor.setCorreo("global.track@gmail.com");
        proveedor.setTelefono("1234-5678");
        proveedor.setDireccion("San Marcos, San Salvador Sur");

        // producto-proveedor
        productoProveedor = new ProductoProveedor();
        productoProveedor.setIdProductoProveedor(1);
        productoProveedor.setProducto(producto);
        productoProveedor.setProveedor(proveedor);
        productoProveedor.setPrecioCompra(BigDecimal.valueOf(79.99));

        // dto de respuesta
        responseDTO = new ProductoProveedorResponseDTO(
                1,
                "Mouse inalambrico",
                "GlobalTrack",
                BigDecimal.valueOf(79.99)
        );

        requestDTO = new ProductoProveedorRequestDTO(
                producto.getIdProducto(),
                proveedor.getIdProveedor(),
                BigDecimal.valueOf(79.99)
        );

        updateDTO = new ProductoProveedorUpdateDTO(
                BigDecimal.valueOf(80.01)
        );
    }

    @Test
    void obtenerPorId_RetornaProductoProveedor(){
        // arrange
        when(repository.findById(1)).thenReturn(Optional.of(productoProveedor));
        when(mapper.toDTO(productoProveedor)).thenReturn(responseDTO);

        //act
        ProductoProveedorResponseDTO resultado = service.obtenerPorId(1);

        // assert
        assertNotNull(resultado);
        assertEquals("Mouse inalambrico", resultado.getProducto());
        assertEquals("GlobalTrack", resultado.getProveedor());
        verify(repository).findById(1);
    }
}
