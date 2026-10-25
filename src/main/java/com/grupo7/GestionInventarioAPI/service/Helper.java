package com.grupo7.GestionInventarioAPI.service;

import com.grupo7.GestionInventarioAPI.exception.AccesoNoAutorizadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import com.grupo7.GestionInventarioAPI.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Helper {
    private final UsuarioRepository repo;

    // para cuando pide usuario pero no valida rol (creo que solo en movimiento se usa(
    public Usuario obtenerUsuarioActivo(Integer idSolicitante) {
        return repo.findByIdUsuarioAndActivoTrue(idSolicitante)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
    }

    // para cuando se requiere validar el usuario (en el de eliminar producto en producto service hay un ejemplo)
    public Usuario exigirAdmin(Integer idSolicitante) {
        Usuario usuario = obtenerUsuarioActivo(idSolicitante);

        if (usuario.getRolUsuario() != RolUsuario.ADMIN) {
            throw new AccesoNoAutorizadoException("Solo un administrador puede realizar esta operacion");
        }
        return usuario;
    }
}
