package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProveedorMapper;
import com.grupo7.GestionInventarioAPI.model.Proveedor;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.repository.ProveedorRepository;
import com.grupo7.GestionInventarioAPI.service.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceImplTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ProveedorMapper proveedorMapper;

    @Mock
    private Helper helper;

    @InjectMocks
    private ProveedorServiceImpl proveedorService;

    private Proveedor proveedor;
    private ProveedorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        proveedor = Proveedor.builder()
                .idProveedor(1)
                .nombreProveedor("Distribuidora Central")
                .correo("central@correo.com")
                .telefono("22223333")
                .direccion("San Salvador")
                .activo(true)
                .build();

        responseDTO = ProveedorResponseDTO.builder()
                .idProveedor(1)
                .nombreProveedor("Distribuidora Central")
                .correo("central@correo.com")
                .telefono("22223333")
                .direccion("San Salvador")
                .build();
    }

    @Test
    void listarActivos_RetornaListaDeProveedores() {
        when(proveedorRepository.findByActivoTrue()).thenReturn(List.of(proveedor));
        when(proveedorMapper.toDTO(proveedor)).thenReturn(responseDTO);

        List<ProveedorResponseDTO> resultado = proveedorService.listarActivos();

        assertEquals(1, resultado.size());
        assertEquals("Distribuidora Central", resultado.get(0).getNombreProveedor());
        verify(proveedorRepository).findByActivoTrue();
    }

    @Test
    void listarActivos_SinProveedores_RetornaListaVacia() {
        when(proveedorRepository.findByActivoTrue()).thenReturn(List.of());

        List<ProveedorResponseDTO> resultado = proveedorService.listarActivos();

        assertTrue(resultado.isEmpty());
        verify(proveedorMapper, never()).toDTO(any());
    }

    @Test
    void obtenerPorId_Existente_RetornaProveedor() {
        when(proveedorRepository.findByIdProveedorAndActivoTrue(1))
                .thenReturn(Optional.of(proveedor));
        when(proveedorMapper.toDTO(proveedor)).thenReturn(responseDTO);

        ProveedorResponseDTO resultado = proveedorService.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdProveedor());
        assertEquals("central@correo.com", resultado.getCorreo());
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(proveedorRepository.findByIdProveedorAndActivoTrue(99))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> proveedorService.obtenerPorId(99));

        verify(proveedorMapper, never()).toDTO(any());
    }

    @Test
    void crear_Valido_GuardaYRetornaProveedor() {
        ProveedorRequestDTO request = new ProveedorRequestDTO();
        request.setNombreProveedor("Distribuidora Central");
        request.setCorreo("central@correo.com");
        request.setTelefono("22223333");
        request.setDireccion("San Salvador");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.existsByCorreo("central@correo.com")).thenReturn(false);
        when(proveedorMapper.toEntity(request)).thenReturn(proveedor);
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);
        when(proveedorMapper.toDTO(proveedor)).thenReturn(responseDTO);

        ProveedorResponseDTO resultado = proveedorService.crear(request, 1);

        assertNotNull(resultado);
        assertEquals("Distribuidora Central", resultado.getNombreProveedor());
        assertTrue(proveedor.isActivo());
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void crear_CorreoDuplicado_LanzaExcepcion() {
        ProveedorRequestDTO request = new ProveedorRequestDTO();
        request.setCorreo("central@correo.com");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.existsByCorreo("central@correo.com")).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class,
                () -> proveedorService.crear(request, 1));

        verify(proveedorRepository, never()).save(any());
        verify(proveedorMapper, never()).toEntity(any());
    }

    @Test
    void actualizar_Valido_ModificaYRetornaProveedor() {
        ProveedorUpdateDTO request = new ProveedorUpdateDTO();
        request.setNombreProveedor("Distribuidora Nueva");
        request.setCorreo("nuevo@correo.com");
        request.setTelefono("77778888");
        request.setDireccion("Santa Ana");

        ProveedorResponseDTO actualizadoDTO = ProveedorResponseDTO.builder()
                .idProveedor(1)
                .nombreProveedor("Distribuidora Nueva")
                .correo("nuevo@correo.com")
                .telefono("77778888")
                .direccion("Santa Ana")
                .build();

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.findByIdProveedorAndActivoTrue(1))
                .thenReturn(Optional.of(proveedor));
        when(proveedorRepository.existsByCorreoAndIdProveedorNot("nuevo@correo.com", 1))
                .thenReturn(false);
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);
        when(proveedorMapper.toDTO(proveedor)).thenReturn(actualizadoDTO);

        ProveedorResponseDTO resultado = proveedorService.actualizar(1, request, 1);

        assertEquals("Distribuidora Nueva", resultado.getNombreProveedor());
        assertEquals("nuevo@correo.com", resultado.getCorreo());
        assertEquals("77778888", resultado.getTelefono());
        assertEquals("Santa Ana", resultado.getDireccion());
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void actualizar_CorreoDuplicado_LanzaExcepcion() {
        ProveedorUpdateDTO request = new ProveedorUpdateDTO();
        request.setCorreo("otro@correo.com");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.findByIdProveedorAndActivoTrue(1))
                .thenReturn(Optional.of(proveedor));
        when(proveedorRepository.existsByCorreoAndIdProveedorNot("otro@correo.com", 1))
                .thenReturn(true);

        assertThrows(RecursoDuplicadoException.class,
                () -> proveedorService.actualizar(1, request, 1));

        verify(proveedorRepository, never()).save(any());
    }

    @Test
    void actualizar_NoExiste_LanzaExcepcion() {
        ProveedorUpdateDTO request = new ProveedorUpdateDTO();
        request.setCorreo("nuevo@correo.com");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.findByIdProveedorAndActivoTrue(99))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> proveedorService.actualizar(99, request, 1));

        verify(proveedorRepository, never()).save(any());
    }

    @Test
    void eliminar_Existente_DesactivaProveedor() {
        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.findByIdProveedorAndActivoTrue(1))
                .thenReturn(Optional.of(proveedor));
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        proveedorService.eliminar(1, 1);

        assertFalse(proveedor.isActivo());
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void eliminar_NoExiste_LanzaExcepcion() {
        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(proveedorRepository.findByIdProveedorAndActivoTrue(99))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> proveedorService.eliminar(99, 1));

        verify(proveedorRepository, never()).save(any());
    }
}
