package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.AccesoNoAutorizadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;
import com.grupo7.GestionInventarioAPI.mapper.ProductoProveedorMapper;
import com.grupo7.GestionInventarioAPI.model.*;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.repository.ProductoProveedorRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
import com.grupo7.GestionInventarioAPI.repository.ProveedorRepository;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoProveedorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoProveedorServiceTest {

    @Mock
    private ProductoProveedorRepository repository;

    @InjectMocks
    private ProductoProveedorServiceImpl service;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private Helper helper;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ProductoProveedorMapper mapper;

    private ProductoProveedor productoProveedor;
    private ProductoProveedorRequestDTO requestDTO;
    private ProductoProveedorResponseDTO responseDTO;
    private ProductoProveedorUpdateDTO updateDTO;

    private Producto producto;
    private Categoria categoria;
    private Proveedor proveedor;
    private Usuario usuario;

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

        //usuario ADMIN de prueba
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombreUsuario("Carlos");
        usuario.setApellidoUsuario("Ayala");
        usuario.setRolUsuario(RolUsuario.ADMIN);

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
    void testObtenerTodasLasAsociaciones(){
        List<ProductoProveedor> productoProveedorList = Collections.singletonList(productoProveedor);
        when(repository.findAll()).thenReturn(productoProveedorList);
        when(mapper.toDTO(productoProveedor)).thenReturn(responseDTO);

        List<ProductoProveedorResponseDTO> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Mouse inalambrico", resultado.get(0).getProducto());
    }

    @Test
    void testObtenerPorId_RetornaProductoProveedor(){
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

    @Test
    void testCrearProductoProveedor_Duplicado(){

        when(productoRepository.findByIdProductoAndActivoTrue(
                producto.getIdProducto()
        )).thenReturn(Optional.of(producto));

        when(proveedorRepository.findByIdProveedorAndActivoTrue(
                proveedor.getIdProveedor()
        )).thenReturn(Optional.of(proveedor));

        when(repository.existsByProducto_IdProductoAndProveedor_IdProveedor(
                producto.getIdProducto(),
                proveedor.getIdProveedor()
        )).thenReturn(true);

        RecursoDuplicadoException exception = assertThrows(
                RecursoDuplicadoException.class,
                () -> service.crearProductoProveedor(requestDTO)
        );

        assertTrue(exception.getMessage().contains(
                "El producto con id " + requestDTO.getIdProducto()
                        + " ya está asociado al proveedor con id "
                        + requestDTO.getIdProveedor()
        ));

        verify(repository, never()).save(any(ProductoProveedor.class));
    }

    @Test
    void testCrearProductoProveedor_Exitoso(){
        when(productoRepository.findByIdProductoAndActivoTrue(
                producto.getIdProducto()
        )).thenReturn(Optional.of(producto));

        when(proveedorRepository.findByIdProveedorAndActivoTrue(
                proveedor.getIdProveedor()
        )).thenReturn(Optional.of(proveedor));

        when(repository.existsByProducto_IdProductoAndProveedor_IdProveedor(
                producto.getIdProducto(),
                proveedor.getIdProveedor()
        )).thenReturn(false);

        when(repository.save(any(ProductoProveedor.class))).thenReturn(productoProveedor);

        when(mapper.toDTO(any(ProductoProveedor.class)))
                .thenReturn(responseDTO);

        ProductoProveedorResponseDTO resultado = service.crearProductoProveedor(requestDTO);

        assertNotNull(resultado);
        assertEquals("Mouse inalambrico", resultado.getProducto());
        verify(repository, times(1)).save(any(ProductoProveedor.class));
    }

    @Test
    void testObtenerProductosPorProveedor(){
        when(proveedorRepository.findByIdProveedorAndActivoTrue(
                proveedor.getIdProveedor()
        )).thenReturn(Optional.of(proveedor));
        List<ProductoProveedor> productoProveedorList = Arrays.asList(productoProveedor);
        when(repository.findByProveedor_IdProveedor(proveedor.getIdProveedor())).thenReturn(productoProveedorList);
        when(mapper.toDTO(productoProveedor)).thenReturn(responseDTO);

        List<ProductoProveedorResponseDTO> resultado = service.obtenerProductosPorProveedor(proveedor.getIdProveedor());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void testObtenerProveedoresPorProducto(){
        when(productoRepository.findByIdProductoAndActivoTrue(
                producto.getIdProducto()
        )).thenReturn(Optional.of(producto));
        List<ProductoProveedor> productoProveedorList = Arrays.asList(productoProveedor);
        when(repository.findByProducto_IdProducto(producto.getIdProducto())).thenReturn(productoProveedorList);
        when(mapper.toDTO(productoProveedor)).thenReturn(responseDTO);

        List<ProductoProveedorResponseDTO> resultado = service.obtenerProveedoresPorProducto(producto.getIdProducto());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void testActualizarProductoProveedor_Exitoso(){
        when(repository.findById(productoProveedor.getIdProductoProveedor())).thenReturn(Optional.of(productoProveedor));
        when(repository.save(any(ProductoProveedor.class))).thenReturn(productoProveedor);
        when(mapper.toDTO(productoProveedor)).thenReturn(responseDTO);

        ProductoProveedorResponseDTO resultado = service.actualizar(1, updateDTO);

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(ProductoProveedor.class));
    }

    @Test
    void testEliminarProductoProveedor_NoAutorizado() {
        when(helper.exigirAdmin(2)).thenThrow(
                new AccesoNoAutorizadoException(
                        "Solo un administrador puede realizar esta operacion"
                )
        );

        AccesoNoAutorizadoException exception = assertThrows(
                AccesoNoAutorizadoException.class,
                () -> service.eliminar(2, 1)
        );

        assertTrue(exception.getMessage().contains(
                "Solo un administrador puede realizar esta operacion"
        ));

        verify(repository, never()).deleteById(1);
        verify(repository, never()).save(any(ProductoProveedor.class));
    }

    @Test
    void testEliminarProductoProveedor_Autorizado() {
        when(helper.exigirAdmin(usuario.getIdUsuario())).thenReturn(usuario);
        when(repository.existsById(1)).thenReturn(true);

        service.eliminar(usuario.getIdUsuario(), 1);

        verify(helper).exigirAdmin(usuario.getIdUsuario());
        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    void testEliminarProductoProveedor_NoExiste() {
        when(helper.exigirAdmin(usuario.getIdUsuario())).thenReturn(usuario);
        when(repository.existsById(99)).thenReturn(false);

        assertThrows(
                RecursoDuplicadoException.class,
                () -> service.eliminar(usuario.getIdUsuario(), 99)
        );

        verify(repository).existsById(99);
        verify(repository, never()).deleteById(anyInt());
    }
}
