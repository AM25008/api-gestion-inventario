package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoServiceImpl productoService;

    //GET /api/productos (ADMIN y OPERADOR)
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar(
            @RequestParam(required = false) Integer idCategoria
    ){
        return ResponseEntity.ok(productoService.listarActivos());
    }

    //GET /api/productos/{id} (ADMIN Y OPERADOR)
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Integer id){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    //GET /api/productos/stock-bajo (ADMIN Y OPERADOR)
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResponseDTO>> consultarStockBajo(){
        return ResponseEntity.ok(productoService.consultarStockBajo());
    }

    //POST /api/productos ADMIN Y OPERADOR
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO creado = productoService.crear(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //PUT /api/productos/{id} ADMIN Y OPERADOR
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoUpdateDTO dto){
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    //DELETE /api/productos/{id} SOLO ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer user
    ){
        productoService.eliminar(id, user);
        return ResponseEntity.noContent().build();
    }
}
