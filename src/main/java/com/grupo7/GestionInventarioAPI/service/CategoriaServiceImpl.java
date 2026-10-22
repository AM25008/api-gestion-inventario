package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.CategoriaMapper;
import com.grupo7.GestionInventarioAPI.model.Categoria;
import com.grupo7.GestionInventarioAPI.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper CategoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarActivas() {
        return categoriaRepository.findByActivaTrue().stream()
                .map(CategoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Integer id) {
        Categoria categoria = categoriaRepository.findByIdCategoriaAndActivaTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada o inactiva con ID: " + id));
        return CategoriaMapper.toResponseDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        // Regla: No pueden existir dos categorías activas con el mismo nombre
        if (categoriaRepository.existsByNombreCategoriaAndActivaTrue(dto.getNombreCategoria())) {
            throw new IllegalArgumentException("Ya existe una categoría activa con el nombre: " + dto.getNombreCategoria());
        }

        Categoria nuevaCategoria = CategoriaMapper.toEntity(dto);
        Categoria guardada = categoriaRepository.save(nuevaCategoria);
        return CategoriaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO actualizar(Integer id, CategoriaUpdateDTO dto) {
        Categoria categoriaExistente = categoriaRepository.findByIdCategoriaAndActivaTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada para actualizar con ID: " + id));

        // Regla: Validar duplicados de nombre excluyendo el ID de la categoría actual
        if (dto.getNombreCategoria() != null && !dto.getNombreCategoria().trim().isEmpty()) {
            if (categoriaRepository.existsByNombreCategoriaAndActivaTrueAndIdCategoriaNot(dto.getNombreCategoria(), id)) {
                throw new IllegalArgumentException("Ya existe otra categoría activa con el nombre: " + dto.getNombreCategoria());
            }
            categoriaExistente.setNombreCategoria(dto.getNombreCategoria());
        }

        if (dto.getDescripcion() != null) {
            categoriaExistente.setDescripcion(dto.getDescripcion());
        }

        Categoria actualizada = categoriaRepository.save(categoriaExistente);
        return CategoriaMapper.toResponseDTO(actualizada);
    }

    @Override
    @Transactional
    public void eliminarLogico(Integer id) {
        Categoria categoria = categoriaRepository.findByIdCategoriaAndActivaTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada para eliminar con ID: " + id));
        
        // Regla: Eliminación es baja lógica
        categoria.setActiva(false);
        categoriaRepository.save(categoria);
    }
}
