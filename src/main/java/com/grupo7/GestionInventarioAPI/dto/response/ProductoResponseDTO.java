package com.grupo7.GestionInventarioAPI.dto.response;

import com.grupo7.GestionInventarioAPI.model.enums.NivelStock;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductoResponseDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String categoria;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer stockMinimo;

    private NivelStock nivelStock;
    private List<String> proveedores; //lista de proveedores
}