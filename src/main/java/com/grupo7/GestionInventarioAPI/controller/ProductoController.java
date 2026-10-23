package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.grupo7.GestionInventarioAPI.exception.AccesoNoAutorizadoException;

import java.util.List;



@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // Validacion de ROLES
    private void validarRol(String rol, boolean soloAdmin){
        if(rol == null){
            throw new AccesoNoAutorizadoException("Acceso denegado. Debe proporcionar un rol.");
        }
        if(soloAdmin && !rol.equals("ADMIN")){
            throw new AccesoNoAutorizadoException("Acceso denegado. Solo un ADMIN puede realizar esta acción.");
        }
        if(!soloAdmin && !rol.equals("ADMIN") && !rol.equals("OPERADOR")){
            throw new AccesoNoAutorizadoException("Acceso denegado. Rol no válido: debe ser ADMIN u OPERADOR.");
        }
    }
    //GET /api/productos (ADMIN y OPERADOR)
    // pienso que hay que dejarlo libre, es decir en los de consulta no validamos usuario, solo en los de eliminar (en esta entidad)
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar(
            @RequestParam(required = false) Integer idCategoria
    ){
        return ResponseEntity.ok(productoService.listarActivos());
    }

    //GET /api/productos/{id} (ADMIN Y OPERADOR)
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(
            @PathVariable Integer id,
            @RequestHeader(value = "Rol-Usuario", required = false) String rolUsuario
    ){
        validarRol(rolUsuario, false);
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    //GET /api/productos/stock-bajo (ADMIN Y OPERADOR)
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResponseDTO>> consultarStockBajo(
            @RequestHeader(value = "Rol-Usuario", required = false) String rolUsuario
    ){
        validarRol(rolUsuario, false);
        return ResponseEntity.ok(productoService.consultarStockBajo());
    }

    //POST /api/productos ADMIN Y OPERADOR
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestBody ProductoRequestDTO dto,
            @RequestHeader(value = "Rol-Usuario", required = false) String rolUsuario
    ){
        validarRol(rolUsuario, false);
        ProductoResponseDTO creado = productoService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    //PUT /api/productos/{id} ADMIN Y OPERADOR
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoUpdateDTO dto,
            @RequestHeader(value = "Rol-Usuario", required = false) String rolUsuario
    ){
        validarRol(rolUsuario, false);
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
