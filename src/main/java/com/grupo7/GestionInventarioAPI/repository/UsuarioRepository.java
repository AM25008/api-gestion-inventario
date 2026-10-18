package com.grupo7.GestionInventarioAPI.repository;

import com.grupo7.GestionInventarioAPI.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByActivoTrue();

    Optional<Usuario> findByIdUsuarioAndActivoTrue(Integer idUsuario);
}
