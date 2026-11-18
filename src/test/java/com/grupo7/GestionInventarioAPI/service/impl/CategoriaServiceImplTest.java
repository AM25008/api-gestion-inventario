package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.CategoriaMapper;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.repository.CategoriaRepository;
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
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @Mock
    private Helper helper;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoria;
    private CategoriaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .idCategoria(1)
                .nombreCategoria("Electrónica")
                .descripcion("Dispositivos")
                .activa(true)
                .build();

        responseDTO = CategoriaResponseDTO.builder()
                .idCategoria(1)
                .nombreCategoria("Electrónica")
                .descripcion("Dispositivos")
                .build();
    }

    @Test
    void listarActivas_RetornaLista() {
        when(categoriaRepository.findByActivaTrue()).thenReturn(List.of(categoria));
        when(categoriaMapper.toResponseDTO(categoria)).thenReturn(responseDTO);

        List<CategoriaResponseDTO> resultado = categoriaService.listarActivas();

        assertEquals(1, resultado.size());
        assertEquals("Electrónica", resultado.get(0).getNombreCategoria());
    }

    @Test
    void obtenerPorId_Existente_RetornaDTO() {
        when(categoriaRepository.findByIdCategoriaAndActivaTrue(1)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponseDTO(categoria)).thenReturn(responseDTO);

        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(1);

        assertEquals(1, resultado.getIdCategoria());
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(categoriaRepository.findByIdCategoriaAndActivaTrue(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> categoriaService.obtenerPorId(99));
    }

    @Test
    void crear_Valido_GuardaYRetornaDTO() {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Electrónica", "Dispositivos");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(categoriaRepository.existsByNombreCategoriaAndActivaTrue("Electrónica")).thenReturn(false);
        when(categoriaMapper.toEntity(request)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toResponseDTO(categoria)).thenReturn(responseDTO);

        CategoriaResponseDTO resultado = categoriaService.crear(request, 1);

        assertNotNull(resultado);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void crear_Duplicada_LanzaExcepcion() {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Electrónica", "Dispositivos");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(categoriaRepository.existsByNombreCategoriaAndActivaTrue("Electrónica")).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> categoriaService.crear(request, 1));
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    void actualizar_Valido_ActualizaYRetornaDTO() {
        CategoriaUpdateDTO update = new CategoriaUpdateDTO("Tecnología", "Nueva desc");
        CategoriaResponseDTO actualizadoDTO = new CategoriaResponseDTO(1, "Tecnología", "Nueva desc");

        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(categoriaRepository.findByIdCategoriaAndActivaTrue(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.existsByNombreCategoriaAndActivaTrueAndIdCategoriaNot("Tecnología", 1)).thenReturn(false);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toResponseDTO(categoria)).thenReturn(actualizadoDTO);

        CategoriaResponseDTO resultado = categoriaService.actualizar(1, update, 1);

        assertEquals("Tecnología", resultado.getNombreCategoria());
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void eliminarLogico_Existente_DesactivaCategoria() {
        when(helper.exigirAdmin(1)).thenReturn(new Usuario());
        when(categoriaRepository.findByIdCategoriaAndActivaTrue(1)).thenReturn(Optional.of(categoria));

        categoriaService.eliminarLogico(1, 1);

        assertFalse(categoria.isActiva());
        verify(categoriaRepository).save(categoria);
    }
}