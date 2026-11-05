package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Productos", description = "Operaciones para la gestión de los productos del inventario")
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoServiceImpl productoService;

    // GET /api/productos
    @Operation(
            summary = "Listar todos los productos",
            description = "Retorna una lista con los productos activos registrados en el sistema"
    )
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar(
            @RequestParam(required = false) Integer idCategoria
    ){
        return ResponseEntity.ok(productoService.listarActivos());
    }

    // GET /api/productos/{id}
    @Operation(
            summary = "Obtener un producto por ID",
            description = "Retorna el producto registrado con el identificador indicado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Integer id){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // GET /api/productos/stock-bajo
    @Operation(
            summary = "Consultar productos con stock bajo",
            description = "Retorna los productos cuyo stock se encuentra por debajo del nivel establecido"
    )
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResponseDTO>> consultarStockBajo(){
        return ResponseEntity.ok(productoService.consultarStockBajo());
    }

    // POST /api/productos
    @Operation(
            summary = "Crear un producto",
            description = "Registra un nuevo producto en el inventario"
    )
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO creado = productoService.crear(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/productos/{id}
    @Operation(
            summary = "Actualizar un producto",
            description = "Actualiza los datos del producto registrado con el identificador indicado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoUpdateDTO dto){
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    // DELETE /api/productos/{id}
    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina el producto registrado con el identificador indicado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer user
    ){
        productoService.eliminar(id, user);
        return ResponseEntity.noContent().build();
    }
}
