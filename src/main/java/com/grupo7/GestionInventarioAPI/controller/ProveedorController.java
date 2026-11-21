package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "5. Proveedores", description = "Operaciones para la gestión de los proveedores")
@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    // GET /api/proveedores
    @Operation(
            summary = "Listar todos los proveedores",
            description = "Retorna una lista con los proveedores activos registrados en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listar() {
        return ResponseEntity.ok(proveedorService.listarActivos());
    }

    // GET /api/proveedores/{id}
    @Operation(
            summary = "Obtener un proveedor por ID",
            description = "Retorna el proveedor registrado con el identificador indicado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    // POST /api/proveedores
    @Operation(
            summary = "Crear un proveedor",
            description = "Registra un nuevo proveedor en el sistema"
    )
    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(
            @Valid @RequestBody ProveedorRequestDTO dto,
            @RequestParam Integer user
    ) {
        ProveedorResponseDTO creado = proveedorService.crear(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/proveedores/{id}
    @Operation(
            summary = "Actualizar un proveedor",
            description = "Actualiza los datos del proveedor registrado con el identificador indicado"
    )
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

    // DELETE /api/proveedores/{id}
    @Operation(
            summary = "Eliminar un proveedor",
            description = "Elimina el proveedor registrado con el identificador indicado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer user
    ) {
        proveedorService.eliminar(id, user);
        return ResponseEntity.noContent().build();
    }
}