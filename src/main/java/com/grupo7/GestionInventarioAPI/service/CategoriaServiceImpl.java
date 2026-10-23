package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;
import com.grupo7.GestionInventarioAPI.exception.AccesoNoAutorizadoException;
import com.grupo7.GestionInventarioAPI.mapper.CategoriaMapper;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.repository.CategoriaRepository;
import com.grupo7.GestionInventarioAPI.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaMapper categoriaMapper;

    // Lógica centralizada de validación 
    private void validarRolAdministrador(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndActivoTrue(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado o inactivo con ID: " + usuarioId));
        
        // Comparación directa contra el Enum oficial RolUsuario
        if (usuario.getRolUsuario() != RolUsuario.ADMIN) {
            throw new AccesoNoAutorizadoException("El usuario con ID " + usuarioId + " no tiene permisos de ADMINISTRADOR para realizar esta acción.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarActivas() {
        return categoriaRepository.findByActivaTrue().stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Integer id) {
        Categoria categoria = categoriaRepository.findByIdCategoriaAndActivaTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada o inactiva con ID: " + id));
        return categoriaMapper.toResponseDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto, Integer usuarioId) {
        validarRolAdministrador(usuarioId);

        if (categoriaRepository.existsByNombreCategoriaAndActivaTrue(dto.getNombreCategoria())) {
            throw new RecursoDuplicadoException("Ya existe una categoría activa con el nombre: " + dto.getNombreCategoria());
        }

        Categoria nuevaCategoria = categoriaMapper.toEntity(dto);
        Categoria guardada = categoriaRepository.save(nuevaCategoria);
        return categoriaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO actualizar(Integer id, CategoriaUpdateDTO dto, Integer usuarioId) {
        validarRolAdministrador(usuarioId);

        Categoria categoriaExistente = categoriaRepository.findByIdCategoriaAndActivaTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada para actualizar con ID: " + id));

        if (dto.getNombreCategoria() != null && !dto.getNombreCategoria().trim().isEmpty()) {
            if (categoriaRepository.existsByNombreCategoriaAndActivaTrueAndIdCategoriaNot(dto.getNombreCategoria(), id)) {
                throw new RecursoDuplicadoException("Ya existe otra categoría activa con el nombre: " + dto.getNombreCategoria());
            }
            categoriaExistente.setNombreCategoria(dto.getNombreCategoria());
        }

        if (dto.getDescripcion() != null) {
            categoriaExistente.setDescripcion(dto.getDescripcion());
        }

        Categoria actualizada = categoriaRepository.save(categoriaExistente);
        return categoriaMapper.toResponseDTO(actualizada);
    }

    @Override
    @Transactional
    public void eliminarLogico(Integer id, Integer usuarioId) {
        validarRolAdministrador(usuarioId);

        Categoria categoria = categoriaRepository.findByIdCategoriaAndActivaTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada para eliminar con ID: " + id));
        
        categoria.setActiva(false);
        categoriaRepository.save(categoria);
    }
}
