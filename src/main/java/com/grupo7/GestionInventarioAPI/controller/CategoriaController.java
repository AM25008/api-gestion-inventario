package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.CategoriaRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.CategoriaUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.CategoriaResponseDTO;
import com.grupo7.GestionInventarioAPI.service.CategoriaService;
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

    private final CategoriaService categoriaService;

    // GET /api/categorias - Acceso: ADMIN y OPERADOR
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarActivas());
    }

    // GET /api/categorias/{id} - Acceso: ADMIN y OPERADOR
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    // POST /api/categorias - Acceso: solo ADMIN
    @PostMapping
    public ResponseEntity<?> crearCategoria(
            @Valid @RequestBody CategoriaRequestDTO dto,
            @RequestHeader(value = "X-User-Role", defaultValue = "OPERADOR") String rol) {
        
        if (!"ADMIN".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error 403: El rol OPERADOR no tiene permisos para crear categorías.");
        }
        
        CategoriaResponseDTO creada = categoriaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT /api/categorias/{id} - Acceso: solo ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaUpdateDTO dto,
            @RequestHeader(value = "X-User-Role", defaultValue = "OPERADOR") String rol) {
        
        if (!"ADMIN".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error 403: El rol OPERADOR no tiene permisos para modificar categorías.");
        }
        
        CategoriaResponseDTO actualizada = categoriaService.actualizar(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE /api/categorias/{id} - Acceso: solo ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", defaultValue = "OPERADOR") String rol) {
        
        if (!"ADMIN".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error 403: El rol OPERADOR no tiene permisos para eliminar categorías.");
        }
        
        categoriaService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
