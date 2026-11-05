package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.UsuarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.UsuarioUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "6. Usuarios", description = "Operaciones para la gestión de los usuarios")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    // GET /api/usuarios
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Retorna la lista de usuarios registrada en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos(
            @RequestParam Integer user) {
        return ResponseEntity.ok(usuarioService.obtenerTodos(user));
    }

    // GET /api/usuarios/{id}
    @Operation(
            summary = "Obtener un usuario por ID",
            description = "Retorna el usuario registrado con el identificador indicado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(
            @PathVariable Integer id,
            @RequestParam Integer user) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id, user));
    }

    // POST /api/usuarios
    @Operation(
            summary = "Crear un usuario",
            description = "Registra un nuevo usuario en el sistema"
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @RequestParam Integer user,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(dto, user));
    }

    // PUT /api/usuarios/{id}
    @Operation(
            summary = "Actualizar un usuario",
            description = "Actualiza los datos del usuario registrado con el identificador indicado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Integer id,
            @RequestParam Integer user,
            @Valid @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto, user));
    }

    // DELETE /api/usuarios/{id}
    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina el usuario registrado con el identificador indicado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer user) {
        usuarioService.eliminar(id, user);
        return ResponseEntity.noContent().build();
    }
}