package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.MovimientoInventarioRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.exception.StockInsuficienteException;
import com.grupo7.GestionInventarioAPI.mapper.MovimientoInventarioMapper;
import com.grupo7.GestionInventarioAPI.model.MovimientoInventario;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.model.Usuario;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import com.grupo7.GestionInventarioAPI.repository.MovimientoInventarioRepository;
import com.grupo7.GestionInventarioAPI.repository.ProductoRepository;
import com.grupo7.GestionInventarioAPI.repository.UsuarioRepository;
import com.grupo7.GestionInventarioAPI.service.MovimientoInventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final MovimientoInventarioMapper mapper;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> obtenerTodos() {
        return movimientoInventarioRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoInventarioResponseDTO obtenerPorId(Integer id) {
        MovimientoInventario mov = movimientoInventarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Transaccion no encontrada"));

        return mapper.toDTO(mov);
    }

    @Override
    @Transactional
    public MovimientoInventarioResponseDTO registrarMovimiento(Integer idUsuario, MovimientoInventarioRequestDTO dto) {

        // Validar que el producto exista
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(dto.getIdProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + dto.getIdProducto() + " no encontrado"));

        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findByIdUsuarioAndActivoTrue(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario con id " + idUsuario + " no encontrado"));

        // operaciones
        if(dto.getTipoMovimiento() == TipoMovimiento.ENTRADA){
            aumentarStock(producto, dto.getCantidad());
        } else{
            disminuirStock(producto, dto.getCantidad());
        }

        MovimientoInventario movimientoInventario = MovimientoInventario.builder()
                .producto(producto)
                .usuario(usuario)
                .tipoMovimiento(dto.getTipoMovimiento())
                .fechaHora(LocalDateTime.now())
                .cantidad(dto.getCantidad())
                .motivo(dto.getMotivo())
                .build();

        movimientoInventarioRepository.save(movimientoInventario);
        productoRepository.save(producto);

        return mapper.toDTO(movimientoInventario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> obtenerMovimientosPorProducto(Integer idProducto) {

        // Verificar que el producto exista
        Producto producto = productoRepository.findByIdProductoAndActivoTrue(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + idProducto + " no encontrado"));

        List<MovimientoInventario> movimientos = movimientoInventarioRepository.findByProducto_IdProducto(idProducto);

        return movimientos.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> obtenerMovimientosPorUsuario(Integer idUsuario) {

        // Verificar que el usuario exista
        Usuario usuario = usuarioRepository.findByIdUsuarioAndActivoTrue(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario con id " + idUsuario + " no encontrado"));

        List<MovimientoInventario> movimientos = movimientoInventarioRepository.findByUsuario_IdUsuario(idUsuario);

        return movimientos.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> obtenerMovimientosPorTipo(TipoMovimiento tipo) {

        List<MovimientoInventario> movimientos = movimientoInventarioRepository.findByTipoMovimiento(tipo);

        return movimientos.stream()
                .map(mapper::toDTO)
                .toList();
    }

    // Aumento de inventario (entrada)
    private void aumentarStock(Producto producto, Integer cantidad) {
        Integer stockActual = producto.getStockActual();

        producto.setStockActual(stockActual + cantidad);
    }

    // Disminucion (salida)
    private void disminuirStock(Producto producto, Integer cantidad){
        Integer stockActual = producto.getStockActual();

        // Validacion de cantidad
        if(cantidad > stockActual){
            throw new StockInsuficienteException("No hay suficiente stock para realizar la transaccion");
        }

        producto.setStockActual(stockActual - cantidad);
    }


}
