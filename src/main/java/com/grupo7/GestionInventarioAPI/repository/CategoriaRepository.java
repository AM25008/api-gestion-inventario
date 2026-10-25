package com.grupo7.GestionInventarioAPI.repository;

import com.grupo7.GestionInventarioAPI.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    
    List<Categoria> findByActivaTrue();

    Optional<Categoria> findByIdCategoriaAndActivaTrue(Integer idCategoria);

    boolean existsByNombreCategoriaAndActivaTrue(String nombreCategoria);
    
    boolean existsByNombreCategoriaAndActivaTrueAndIdCategoriaNot(String nombreCategoria, Integer idCategoria);
}
