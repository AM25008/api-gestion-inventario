package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.service.impl.ProductoProveedorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "4. Productos y proveedores", description = "Operaciones para gestionar las asociaciones entre productos y proveedores")
@RestController
@RequestMapping("/api/producto-proveedor")
@RequiredArgsConstructor
public class ProductoProveedorController {

    private final ProductoProveedorServiceImpl service;

    // GET /api/producto-proveedor
    @Operation(
            summary = "Listar todas las asociaciones",
            description = "Retorna una lista con todas las asociaciones entre productos y proveedores"
    )
    @GetMapping
    public ResponseEntity<List<ProductoProveedorResponseDTO>> obtenerTodos(){
        List<ProductoProveedorResponseDTO> productoProv = service.obtenerTodos();

        return ResponseEntity.ok(productoProv);
    }

    // GET /api/producto-proveedor/{id}
    @Operation(
            summary = "Obtener una asociación por ID",
            description = "Retorna la asociación entre producto y proveedor con el identificador indicado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductoProveedorResponseDTO> obtenerPorId(@PathVariable Integer id){
        ProductoProveedorResponseDTO productoProveedor = service.obtenerPorId(id);

        return ResponseEntity.ok(productoProveedor);
    }

    // POST /api/producto-proveedor
    @Operation(
            summary = "Asignar un producto a un proveedor",
            description = "Crea una asociación entre un producto y un proveedor"
    )
    @PostMapping
    public ResponseEntity<ProductoProveedorResponseDTO> asignarProductoProveedor(@Valid @RequestBody ProductoProveedorRequestDTO dto){
        ProductoProveedorResponseDTO nuevo = service.crearProductoProveedor(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // GET /api/producto-proveedor/proveedor/{idProveedor}
    @Operation(
            summary = "Listar productos por proveedor",
            description = "Retorna las asociaciones de productos correspondientes al proveedor indicado"
    )
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<ProductoProveedorResponseDTO>> obtenerProductosPorProveedor(@PathVariable Integer idProveedor){
        List<ProductoProveedorResponseDTO> productoProv = service.obtenerProductosPorProveedor(idProveedor);

        return ResponseEntity.ok(productoProv);
    }

    // GET /api/producto-proveedor/producto/{idProducto}
    @Operation(
            summary = "Listar proveedores por producto",
            description = "Retorna las asociaciones de proveedores correspondientes al producto indicado"
    )
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ProductoProveedorResponseDTO>> obtenerProveedoresPorProducto(@PathVariable Integer idProducto){
        List<ProductoProveedorResponseDTO> productoProv = service.obtenerProveedoresPorProducto(idProducto);

        return ResponseEntity.ok(productoProv);
    }

    // PUT /api/producto-proveedor/{id}
    @Operation(
            summary = "Actualizar una asociación",
            description = "Actualiza la asociación entre producto y proveedor con el identificador indicado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductoProveedorResponseDTO> actualizarAsociacion(@PathVariable Integer id,
                                                                             @Valid @RequestBody ProductoProveedorUpdateDTO dto
                                                                             ){
        ProductoProveedorResponseDTO actualizado = service.actualizar(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/producto-proveedor/{id}
    @Operation(
            summary = "Eliminar una asociación",
            description = "Elimina la asociación entre producto y proveedor con el identificador indicado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id, @RequestParam Integer user){
        service.eliminar(user, id);

        return ResponseEntity.noContent().build();
    }
}
