package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.UsuarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.UsuarioUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.AccesoNoAutorizadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.UsuarioMapper;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.repository.UsuarioRepository;
import com.grupo7.GestionInventarioAPI.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    /**
     * Valida que el usuario emisor exista, esté activo y sea ADMINISTRADOR.
     */
    private Usuario validarAdmin(Integer idUsuarioEmisor) {
        Usuario emisor = usuarioRepository.findByIdUsuarioAndActivoTrue(idUsuarioEmisor)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario emisor no encontrado o inactivo con ID: " + idUsuarioEmisor));

        if (emisor.getRolUsuario() != RolUsuario.ADMIN) {
            throw new AccesoNoAutorizadoException("Operación no autorizada: Solo los administradores pueden realizar esta acción");
        }

        return emisor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos(Integer idUsuarioEmisor) {
        validarAdmin(idUsuarioEmisor);
        return usuarioRepository.findByActivoTrue().stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Integer id, Integer idUsuarioEmisor) {
        validarAdmin(idUsuarioEmisor);
        Usuario usuario = usuarioRepository.findByIdUsuarioAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto, Integer idUsuarioEmisor) {
        validarAdmin(idUsuarioEmisor);
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Integer id, UsuarioUpdateDTO dto, Integer idUsuarioEmisor) {
        validarAdmin(idUsuarioEmisor);

        Usuario usuario = usuarioRepository.findByIdUsuarioAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        // Actualización parcial
        if (dto.getNombreUsuario() != null && !dto.getNombreUsuario().isBlank()) {
            usuario.setNombreUsuario(dto.getNombreUsuario());
        }
        if (dto.getApellidoUsuario() != null && !dto.getApellidoUsuario().isBlank()) {
            usuario.setApellidoUsuario(dto.getApellidoUsuario());
        }
        if (dto.getRol() != null) {
            usuario.setRolUsuario(dto.getRol());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id, Integer idUsuarioEmisor) {
        validarAdmin(idUsuarioEmisor);

        // Regla de negocio: Un administrador no puede desactivar su propia cuenta
        if (id.equals(idUsuarioEmisor)) {
            throw new IllegalArgumentException("Un administrador no puede desactivar su propia cuenta");
        }

        Usuario usuario = usuarioRepository.findByIdUsuarioAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        // Baja lógica
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}