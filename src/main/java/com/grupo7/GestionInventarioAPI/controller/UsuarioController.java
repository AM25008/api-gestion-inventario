package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.UsuarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.UsuarioUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.UsuarioResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    // GET
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos(
            @RequestParam Integer user) {
        return ResponseEntity.ok(usuarioService.obtenerTodos(user));
    }

    // GET
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(
            @PathVariable Integer id,
            @RequestParam Integer user) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id, user));
    }

    // POST 
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @RequestParam Integer user,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(dto, user));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Integer id,
            @RequestParam Integer user,
            @Valid @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto, user));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer user) {
        usuarioService.eliminar(id, user);
        return ResponseEntity.noContent().build();
    }
}