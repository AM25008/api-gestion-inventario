package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.CategoriaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. Categorias", description = "Operaciones para la gestión de las categorías de los productos")
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaServiceImpl categoriaService;

    // GET /api/categorias
    @Operation(
            summary = "Listar todas las categorías",
            description = "Retorna una lista con las categorías de productos registradas en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarActivas());
    }

    // GET /api/categorias/{id}
    @Operation(
            summary = "Obtener una categoría por ID",
            description = "Retorna la categoría registrada con el identificador indicado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    // POST /api/categorias
    @Operation(
            summary = "Crear una categoría",
            description = "Registra una nueva categoría de productos en el sistema"
    )
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @Valid @RequestBody CategoriaRequestDTO dto,
            @RequestParam Integer user) {
        
        CategoriaResponseDTO creada = categoriaService.crear(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT /api/categorias/{id}
    @Operation(
            summary = "Actualizar una categoría",
            description = "Actualiza los datos de la categoría registrada con el identificador indicado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaUpdateDTO dto,
            @RequestParam Integer user) {
        
        CategoriaResponseDTO actualizada = categoriaService.actualizar(id, dto, user);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE /api/categorias/{id}
    @Operation(
            summary = "Eliminar una categoría",
            description = "Realiza la eliminación lógica de la categoría registrada con el identificador indicado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Integer id,
            @RequestParam Integer user) {
        
        categoriaService.eliminarLogico(id, user);
        return ResponseEntity.noContent().build();
    }
}
