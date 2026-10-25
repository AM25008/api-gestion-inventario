package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.response.MovimientoInventarioResponseDTO;
import com.grupo7.GestionInventarioAPI.model.MovimientoInventario;
import org.springframework.stereotype.Component;

@Component
public class MovimientoInventarioMapper {

    public MovimientoInventarioResponseDTO toDTO(MovimientoInventario mov){
        if(mov == null) return null;

        return MovimientoInventarioResponseDTO.builder()
                .idMovimiento(mov.getIdMovimiento())
                .producto(mov.getProducto().getNombreProducto())
                .usuario(mov.getUsuario().getNombreUsuario() + " " + mov.getUsuario().getApellidoUsuario())
                .tipoMovimiento(mov.getTipoMovimiento())
                .fechaHora(mov.getFechaHora())
                .cantidad(mov.getCantidad())
                .motivo(mov.getMotivo()).build();
    }
}
