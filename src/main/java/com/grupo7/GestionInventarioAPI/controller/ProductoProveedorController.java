package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoProveedorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-proveedor")
@RequiredArgsConstructor
public class ProductoProveedorController {

    private final ProductoProveedorServiceImpl service;

    @GetMapping
    public ResponseEntity<List<ProductoProveedorResponseDTO>> obtenerTodos(){
        List<ProductoProveedorResponseDTO> productoProv = service.obtenerTodos();

        return ResponseEntity.ok(productoProv);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoProveedorResponseDTO> obtenerPorId(@PathVariable Integer id){
        ProductoProveedorResponseDTO productoProveedor = service.obtenerPorId(id);

        return ResponseEntity.ok(productoProveedor);
    }

    @PostMapping
    public ResponseEntity<ProductoProveedorResponseDTO> asignarProductoProveedor(@Valid @RequestBody ProductoProveedorRequestDTO dto){
        ProductoProveedorResponseDTO nuevo = service.crearProductoProveedor(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<ProductoProveedorResponseDTO>> obtenerProductosPorProveedor(@PathVariable Integer idProveedor){
        List<ProductoProveedorResponseDTO> productoProv = service.obtenerProductosPorProveedor(idProveedor);

        return ResponseEntity.ok(productoProv);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ProductoProveedorResponseDTO>> obtenerProveedoresPorProducto(@PathVariable Integer idProducto){
        List<ProductoProveedorResponseDTO> productoProv = service.obtenerProveedoresPorProducto(idProducto);

        return ResponseEntity.ok(productoProv);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoProveedorResponseDTO> actualizarAsociacion(@PathVariable Integer id,
                                                                             @Valid @RequestBody ProductoProveedorUpdateDTO dto
                                                                             ){
        ProductoProveedorResponseDTO actualizado = service.actualizar(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id, @RequestParam Integer user){
        service.eliminar(user, id);

        return ResponseEntity.noContent().build();
    }
}
