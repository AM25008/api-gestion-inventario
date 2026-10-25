package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    /*
    private void validarRol(String rol, boolean soloAdmin) {
        if (rol == null) {
            throw new AccesoNoAutorizadoException(
                    "Debe proporcionar el rol del usuario."
            );
        }

        if (soloAdmin && !rol.equals("ADMIN")) {
            throw new AccesoNoAutorizadoException(
                    "Solo un ADMIN puede realizar esta acción."
            );
        }

        if (!soloAdmin && !rol.equals("ADMIN")
                && !rol.equals("OPERADOR")) {
            throw new AccesoNoAutorizadoException(
                    "El rol debe ser ADMIN u OPERADOR."
            );
        }
    }*/

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listar(String rolUsuario) {
        return ResponseEntity.ok(proveedorService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(
            @Valid @RequestBody ProveedorRequestDTO dto,
            @RequestParam Integer user
    ) {
        ProveedorResponseDTO creado = proveedorService.crear(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorUpdateDTO dto,
            @RequestParam Integer user
    ) {
        return ResponseEntity.ok(
                proveedorService.actualizar(id, dto, user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer user
    ) {
        proveedorService.eliminar(id, user);
        return ResponseEntity.noContent().build();
    }
}