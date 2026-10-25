package com.grupo7.GestionInventarioAPI.controller;

import com.grupo7.GestionInventarioAPI.dto.request.MovimientoInventarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import com.grupo7.GestionInventarioAPI.service.impl.MovimientoInventarioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioServiceImpl service;

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerTodos(){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerTodos();

        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioResponseDTO> obtenerPorId(@PathVariable Integer id){

        MovimientoInventarioResponseDTO movimiento = service.obtenerPorId(id);

        return ResponseEntity.ok(movimiento);
    }

    @PostMapping()
    public ResponseEntity<MovimientoInventarioResponseDTO> registrarMovimiento(@RequestParam Integer user, @Valid @RequestBody MovimientoInventarioRequestDTO dto){

        MovimientoInventarioResponseDTO nuevo = service.registrarMovimiento(user, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerMovimientosPorProducto(@PathVariable Integer idProducto){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerMovimientosPorProducto(idProducto);

        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerMovimientosPorUsuario(@PathVariable Integer idUsuario){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerMovimientosPorUsuario(idUsuario);

        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/tipoMovimiento/{tipoMovimiento}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerMovimientosPorTipo(@PathVariable TipoMovimiento tipoMovimiento){

        List<MovimientoInventarioResponseDTO> movimientos = service.obtenerMovimientosPorTipo(tipoMovimiento);

        return ResponseEntity.ok(movimientos);
    }
}
