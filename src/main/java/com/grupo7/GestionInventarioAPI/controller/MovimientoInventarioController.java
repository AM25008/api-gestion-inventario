package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.MovimientoInventarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import com.grupo7.GestionInventarioAPI.service.impl.MovimientoInventarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Movimientos de inventario", description = "Operaciones para la gestión de los movimientos del inventario")
@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioServiceImpl service;

    // GET /api/movimientos
    @Operation(
            summary = "Listar todos los movimientos",
            description = "Retorna una lista con todos los movimientos de inventario registrados"
    )
    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerTodos(){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerTodos();

        return ResponseEntity.ok(movimientos);
    }

    // GET /api/movimientos/{id}
    @Operation(
            summary = "Obtener un movimiento por ID",
            description = "Retorna el movimiento de inventario registrado con el identificador indicado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioResponseDTO> obtenerPorId(@PathVariable Integer id){

        MovimientoInventarioResponseDTO movimiento = service.obtenerPorId(id);

        return ResponseEntity.ok(movimiento);
    }

    // POST /api/movimientos
    @Operation(
            summary = "Registrar un movimiento",
            description = "Registra un nuevo movimiento de inventario"
    )
    @PostMapping()
    public ResponseEntity<MovimientoInventarioResponseDTO> registrarMovimiento(@RequestParam Integer user, @Valid @RequestBody MovimientoInventarioRequestDTO dto){

        MovimientoInventarioResponseDTO nuevo = service.registrarMovimiento(user, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // GET /api/movimientos/producto/{idProducto}
    @Operation(
            summary = "Listar movimientos por producto",
            description = "Retorna los movimientos de inventario asociados al producto indicado"
    )
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerMovimientosPorProducto(@PathVariable Integer idProducto){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerMovimientosPorProducto(idProducto);

        return ResponseEntity.ok(movimientos);
    }

    // GET /api/movimientos/usuario/{idUsuario}
    @Operation(
            summary = "Listar movimientos por usuario",
            description = "Retorna los movimientos de inventario asociados al usuario indicado"
    )
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerMovimientosPorUsuario(@PathVariable Integer idUsuario){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerMovimientosPorUsuario(idUsuario);

        return ResponseEntity.ok(movimientos);
    }

    // GET /api/movimientos/tipoMovimiento/{tipoMovimiento}
    @Operation(
            summary = "Listar movimientos por tipo",
            description = "Retorna los movimientos de inventario que corresponden al tipo indicado"
    )
    @GetMapping("/tipoMovimiento/{tipoMovimiento}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerMovimientosPorTipo(@PathVariable TipoMovimiento tipoMovimiento){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerMovimientosPorTipo(tipoMovimiento);

        return ResponseEntity.ok(movimientos);
    }
}
