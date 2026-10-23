package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.CategoriaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaServiceImpl categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @Valid @RequestBody CategoriaRequestDTO dto,
            @RequestParam Integer user) {
        
        CategoriaResponseDTO creada = categoriaService.crear(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaUpdateDTO dto,
            @RequestParam Integer user) {
        
        CategoriaResponseDTO actualizada = categoriaService.actualizar(id, dto, user);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Integer id,
            @RequestParam Integer user) {
        
        categoriaService.eliminarLogico(id, user);
        return ResponseEntity.noContent().build();
    }
}
