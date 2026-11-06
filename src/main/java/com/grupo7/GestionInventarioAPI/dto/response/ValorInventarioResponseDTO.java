package com.grupo7.GestionInventarioAPI.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorInventarioResponseDTO {
    private BigDecimal valorTotal;
    private Long cantidadProductos;
    private Long unidadesTotales;
}
