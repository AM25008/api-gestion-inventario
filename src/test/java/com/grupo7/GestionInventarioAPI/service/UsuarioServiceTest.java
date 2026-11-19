package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.UsuarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.UsuarioUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.AccesoNoAutorizadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.UsuarioMapper;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.repository.UsuarioRepository;
import com.grupo7.GestionInventarioAPI.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario adminEmisor;
    private Usuario usuarioNormal;
    private UsuarioRequestDTO requestDTO;
    private UsuarioUpdateDTO updateDTO;
    private UsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // usuario ADMIN emisor de prueba
        adminEmisor = new Usuario();
        adminEmisor.setIdUsuario(1);
        adminEmisor.setNombreUsuario("Carlos");
        adminEmisor.setApellidoUsuario("Ayala");
        adminEmisor.setRolUsuario(RolUsuario.ADMIN);
        adminEmisor.setActivo(true);

        // usuario objetivo de prueba
        usuarioNormal = new Usuario();
        usuarioNormal.setIdUsuario(2);
        usuarioNormal.setNombreUsuario("Juan");
        usuarioNormal.setApellidoUsuario("Perez");
        usuarioNormal.setRolUsuario(RolUsuario.ADMIN);
        usuarioNormal.setActivo(true);

        // request DTO de prueba
        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNombreUsuario("Juan");
        requestDTO.setApellidoUsuario("Perez");
        requestDTO.setRol(RolUsuario.ADMIN);

        // update DTO de prueba
        updateDTO = new UsuarioUpdateDTO();
        updateDTO.setNombreUsuario("Juan Carlos");
        updateDTO.setApellidoUsuario("Perez Gomez");
        updateDTO.setRol(RolUsuario.ADMIN);

        // dto de respuesta
        responseDTO = new UsuarioResponseDTO();
        responseDTO.setIdUsuario(2);
        responseDTO.setNombreUsuario("Juan");
        responseDTO.setApellidoUsuario("Perez");
        responseDTO.setRol(RolUsuario.ADMIN);
    }

    @Test
    void testObtenerTodos_Exitoso() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));
        when(usuarioRepository.findByActivoTrue())
                .thenReturn(Collections.singletonList(usuarioNormal));
        when(usuarioMapper.toResponseDTO(usuarioNormal)).thenReturn(responseDTO);

        List<UsuarioResponseDTO> resultado = usuarioService.obtenerTodos(adminEmisor.getIdUsuario());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombreUsuario());
        verify(usuarioRepository, times(1)).findByActivoTrue();
    }

    @Test
    void testObtenerTodos_EmisorNoEsAdmin_LanzaExcepcion() {
        adminEmisor.setRolUsuario(null); // O un rol diferente a ADMIN
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));

        AccesoNoAutorizadoException exception = assertThrows(
                AccesoNoAutorizadoException.class,
                () -> usuarioService.obtenerTodos(adminEmisor.getIdUsuario())
        );

        assertTrue(exception.getMessage().contains(
                "Operación no autorizada: Solo los administradores pueden realizar esta acción"
        ));
        verify(usuarioRepository, never()).findByActivoTrue();
    }

    @Test
    void testObtenerPorId_Exitoso() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(usuarioNormal.getIdUsuario()))
                .thenReturn(Optional.of(usuarioNormal));
        when(usuarioMapper.toResponseDTO(usuarioNormal)).thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.obtenerPorId(
                usuarioNormal.getIdUsuario(),
                adminEmisor.getIdUsuario()
        );

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreUsuario());
        verify(usuarioRepository, times(2)).findByIdUsuarioAndActivoTrue(anyInt());
    }

    @Test
    void testObtenerPorId_NoEncontrado() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(99))
                .thenReturn(Optional.empty());

        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> usuarioService.obtenerPorId(99, adminEmisor.getIdUsuario())
        );

        assertTrue(exception.getMessage().contains("Usuario no encontrado con ID: 99"));
    }

    @Test
    void testCrearUsuario_Exitoso() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));
        when(usuarioMapper.toEntity(requestDTO)).thenReturn(usuarioNormal);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioNormal);
        when(usuarioMapper.toResponseDTO(usuarioNormal)).thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.crear(requestDTO, adminEmisor.getIdUsuario());

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreUsuario());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActualizarUsuario_Exitoso() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(usuarioNormal.getIdUsuario()))
                .thenReturn(Optional.of(usuarioNormal));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioNormal);
        when(usuarioMapper.toResponseDTO(usuarioNormal)).thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.actualizar(
                usuarioNormal.getIdUsuario(),
                updateDTO,
                adminEmisor.getIdUsuario()
        );

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario_Exitoso() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(usuarioNormal.getIdUsuario()))
                .thenReturn(Optional.of(usuarioNormal));

        usuarioService.eliminar(usuarioNormal.getIdUsuario(), adminEmisor.getIdUsuario());

        // Uso de isActivo() generado por Lombok para boolean primitivo
        assertFalse(usuarioNormal.isActivo());
        verify(usuarioRepository, times(1)).save(usuarioNormal);
    }

    @Test
    void testEliminarUsuario_MismoAdmin_LanzaExcepcion() {
        when(usuarioRepository.findByIdUsuarioAndActivoTrue(adminEmisor.getIdUsuario()))
                .thenReturn(Optional.of(adminEmisor));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.eliminar(adminEmisor.getIdUsuario(), adminEmisor.getIdUsuario())
        );

        assertTrue(exception.getMessage().contains("Un administrador no puede desactivar su propia cuenta"));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}