package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.MovimientoInventarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.exception.StockInsuficienteException;
import com.grupo7.GestionInventarioAPI.mapper.MovimientoInventarioMapper;
import com.grupo7.GestionInventarioAPI.model.MovimientoInventario;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import com.grupo7.GestionInventarioAPI.repository.MovimientoInventarioRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
import com.grupo7.GestionInventarioAPI.repository.UsuarioRepository;
import com.grupo7.GestionInventarioAPI.service.impl.MovimientoInventarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovimientoInventarioTest {

    @Mock
    private MovimientoInventarioRepository repository;

    @Mock
    private MovimientoInventarioMapper mapper;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Helper helper;

    @InjectMocks
    private MovimientoInventarioServiceImpl service;

    private Producto producto;
    private Usuario usuario;
    private MovimientoInventario movimiento;
    private MovimientoInventarioRequestDTO requestDTO;
    private MovimientoInventarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .idProducto(1)
                .nombreProducto("Mouse inalambrico")
                .precio(BigDecimal.valueOf(89.99))
                .stockActual(10)
                .build();

        usuario = Usuario.builder()
                .idUsuario(1)
                .nombreUsuario("Carlos")
                .apellidoUsuario("Ayala")
                .rolUsuario(RolUsuario.ADMIN)
                .build();

        movimiento = MovimientoInventario.builder()
                .idMovimiento(1)
                .producto(producto)
                .usuario(usuario)
                .tipoMovimiento(TipoMovimiento.ENTRADA)
                .fechaHora(LocalDateTime.now())
                .cantidad(5)
                .motivo("Reposicion de inventario")
                .build();

        requestDTO = crearRequest(TipoMovimiento.ENTRADA, 5);
        responseDTO = MovimientoInventarioResponseDTO.builder()
                .idMovimiento(1)
                .producto("Mouse inalambrico")
                .usuario("Carlos Ayala")
                .tipoMovimiento(TipoMovimiento.ENTRADA)
                .fechaHora(movimiento.getFechaHora())
                .cantidad(5)
                .motivo("Reposicion de inventario")
                .build();
    }

    @Test
    void testObtenerTodos() {
        when(repository.findAll()).thenReturn(Collections.singletonList(movimiento));
        when(mapper.toDTO(movimiento)).thenReturn(responseDTO);

        List<MovimientoInventarioResponseDTO> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Mouse inalambrico", resultado.get(0).getProducto());
        verify(mapper).toDTO(movimiento);
    }

    @Test
    void testObtenerPorId_RetornaMovimiento() {
        when(repository.findById(1)).thenReturn(Optional.of(movimiento));
        when(mapper.toDTO(movimiento)).thenReturn(responseDTO);

        MovimientoInventarioResponseDTO resultado = service.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdMovimiento());
        assertEquals(TipoMovimiento.ENTRADA, resultado.getTipoMovimiento());
        verify(repository).findById(1);
    }

    @Test
    void testObtenerPorId_NoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerPorId(99)
        );

        assertEquals("Transaccion no encontrada", exception.getMessage());
        verify(mapper, never()).toDTO(any(MovimientoInventario.class));
    }

    @Test
    void testRegistrarMovimiento_EntradaExitosa() {
        when(productoRepository.findByIdProductoAndActivoTrue(1)).thenReturn(Optional.of(producto));
        when(helper.obtenerUsuarioActivo(1)).thenReturn(usuario);
        when(mapper.toDTO(any(MovimientoInventario.class))).thenReturn(responseDTO);

        MovimientoInventarioResponseDTO resultado = service.registrarMovimiento(1, requestDTO);

        assertNotNull(resultado);
        assertEquals(15, producto.getStockActual());
        ArgumentCaptor<MovimientoInventario> movimientoCaptor =
                ArgumentCaptor.forClass(MovimientoInventario.class);
        verify(repository).save(movimientoCaptor.capture());
        verify(productoRepository).save(producto);
        MovimientoInventario movimientoGuardado = movimientoCaptor.getValue();
        assertSame(producto, movimientoGuardado.getProducto());
        assertSame(usuario, movimientoGuardado.getUsuario());
        assertEquals(TipoMovimiento.ENTRADA, movimientoGuardado.getTipoMovimiento());
        assertEquals(5, movimientoGuardado.getCantidad());
        assertEquals("Reposicion de inventario", movimientoGuardado.getMotivo());
        assertNotNull(movimientoGuardado.getFechaHora());
    }

    @Test
    void testRegistrarMovimiento_SalidaExitosa() {
        requestDTO = crearRequest(TipoMovimiento.SALIDA, 4);
        when(productoRepository.findByIdProductoAndActivoTrue(1)).thenReturn(Optional.of(producto));
        when(helper.obtenerUsuarioActivo(1)).thenReturn(usuario);
        when(mapper.toDTO(any(MovimientoInventario.class))).thenReturn(responseDTO);

        MovimientoInventarioResponseDTO resultado = service.registrarMovimiento(1, requestDTO);

        assertNotNull(resultado);
        assertEquals(6, producto.getStockActual());
        verify(repository).save(any(MovimientoInventario.class));
        verify(productoRepository).save(producto);
    }

    @Test
    void testRegistrarMovimiento_ProductoNoEncontrado() {
        when(productoRepository.findByIdProductoAndActivoTrue(1)).thenReturn(Optional.empty());

        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.registrarMovimiento(1, requestDTO)
        );

        assertTrue(exception.getMessage().contains("Producto con id 1 no encontrado"));
        verifyNoInteractions(helper);
        verify(repository, never()).save(any(MovimientoInventario.class));
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testRegistrarMovimiento_UsuarioNoEncontrado() {
        when(productoRepository.findByIdProductoAndActivoTrue(1)).thenReturn(Optional.of(producto));
        when(helper.obtenerUsuarioActivo(1)).thenThrow(
                new RecursoNoEncontradoException("Usuario no encontrado")
        );

        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.registrarMovimiento(1, requestDTO)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        assertEquals(10, producto.getStockActual());
        verify(repository, never()).save(any(MovimientoInventario.class));
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testRegistrarMovimiento_StockInsuficiente() {
        requestDTO = crearRequest(TipoMovimiento.SALIDA, 11);
        when(productoRepository.findByIdProductoAndActivoTrue(1)).thenReturn(Optional.of(producto));
        when(helper.obtenerUsuarioActivo(1)).thenReturn(usuario);

        StockInsuficienteException exception = assertThrows(
                StockInsuficienteException.class,
                () -> service.registrarMovimiento(1, requestDTO)
        );

        assertEquals(
                "No hay suficiente stock para realizar la transaccion",
                exception.getMessage()
        );
        assertEquals(10, producto.getStockActual());
        verify(repository, never()).save(any(MovimientoInventario.class));
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testObtenerMovimientosPorProducto() {
        when(productoRepository.findByIdProductoAndActivoTrue(1)).thenReturn(Optional.of(producto));
        when(repository.findByProducto_IdProducto(1)).thenReturn(Collections.singletonList(movimiento));
        when(mapper.toDTO(movimiento)).thenReturn(responseDTO);

        List<MovimientoInventarioResponseDTO> resultado = service.obtenerMovimientosPorProducto(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Mouse inalambrico", resultado.get(0).getProducto());
        verify(repository).findByProducto_IdProducto(1);
    }

    @Test
    void testObtenerMovimientosPorProducto_NoEncontrado() {
        when(productoRepository.findByIdProductoAndActivoTrue(99)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerMovimientosPorProducto(99)
        );
        verify(repository, never()).findByProducto_IdProducto(anyInt());
    }

    @Test
    void testObtenerMovimientosPorUsuario() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(1)).thenReturn(Optional.of(usuario));
        when(repository.findByUsuario_IdUsuario(1)).thenReturn(Collections.singletonList(movimiento));
        when(mapper.toDTO(movimiento)).thenReturn(responseDTO);

        List<MovimientoInventarioResponseDTO> resultado = service.obtenerMovimientosPorUsuario(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos Ayala", resultado.get(0).getUsuario());
        verify(repository).findByUsuario_IdUsuario(1);
    }

    @Test
    void testObtenerMovimientosPorUsuario_NoEncontrado() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(99)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerMovimientosPorUsuario(99)
        );
        verify(repository, never()).findByUsuario_IdUsuario(anyInt());
    }

    @Test
    void testObtenerMovimientosPorTipo() {
        when(repository.findByTipoMovimiento(TipoMovimiento.ENTRADA))
                .thenReturn(Collections.singletonList(movimiento));
        when(mapper.toDTO(movimiento)).thenReturn(responseDTO);

        List<MovimientoInventarioResponseDTO> resultado =
                service.obtenerMovimientosPorTipo(TipoMovimiento.ENTRADA);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(TipoMovimiento.ENTRADA, resultado.get(0).getTipoMovimiento());
        verify(repository).findByTipoMovimiento(TipoMovimiento.ENTRADA);
    }

    private MovimientoInventarioRequestDTO crearRequest(TipoMovimiento tipoMovimiento, int cantidad) {
        MovimientoInventarioRequestDTO dto = new MovimientoInventarioRequestDTO();
        dto.setIdProducto(1);
        dto.setTipoMovimiento(tipoMovimiento);
        dto.setCantidad(cantidad);
        dto.setMotivo("Reposicion de inventario");
        return dto;
    }
}
