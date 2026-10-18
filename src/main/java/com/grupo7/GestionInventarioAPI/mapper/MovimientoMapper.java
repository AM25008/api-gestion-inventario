package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.response.MovimientoResponseDTO;
import com.grupo7.GestionInventarioAPI.model.MovimientoInventario;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public MovimientoResponseDTO toDTO(MovimientoInventario mov){
        if(mov == null) return null;

        return MovimientoResponseDTO.builder()
                .idMovimiento(mov.getIdMovimiento())
                .producto(mov.getProducto().getNombreProducto())
                .usuario(mov.getUsuario().getNombreUsuario())
                .tipoMovimiento(mov.getTipoMovimiento())
                .fechaHora(mov.getFechaHora())
                .cantidad(mov.getCantidad())
                .motivo(mov.getMotivo()).build();
    }
}
