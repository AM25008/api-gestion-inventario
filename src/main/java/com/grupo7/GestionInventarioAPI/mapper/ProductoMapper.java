package com.grupo7.GestionInventarioAPI.mapper;

import com.grupo7.GestionInventarioAPI.dto.request.ProductoRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProductoUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProductoResponseDTO;
import com.grupo7.GestionInventarioAPI.model.Producto;
import com.grupo7.GestionInventarioAPI.model.enums.NivelStock;
import org.springframework.stereotype.Component;

@Component

public class ProductoMapper {

    public Producto toEntity(ProductoRequestDTO dto){
        if(dto == null) return null;

        return Producto.builder()
                .nombreProducto(dto.getNombreProducto())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stockActual(dto.getStock())
                .stockMinimo(dto.getStockMinimo())
                .build();
    }

    public ProductoResponseDTO toDTO(Producto entity){
        if(entity == null) return null;

        return ProductoResponseDTO.builder()
                .idProducto(entity.getIdProducto())
                .nombreProducto(entity.getNombreProducto())
                .categoria(entity.getCategoria() != null ? entity.getCategoria().getNombreCategoria(): "Sin categoria")
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .stock(entity.getStockActual())
                .stockMinimo(entity.getStockMinimo())
                .nivelStock(calcularNivelStock(entity.getStockActual(), entity.getStockMinimo()))
                .build();
    }

    public void updateEntityFromDTO(ProductoUpdateDTO dto, Producto entity){
        if(dto == null || entity == null) return;

        if(dto.getNombreProducto() != null){
            entity.setNombreProducto(dto.getNombreProducto());
        }

        if(dto.getDescripcion() != null){
            entity.setDescripcion(dto.getDescripcion());
        }
        if(dto.getPrecio() != null){
            entity.setPrecio(dto.getPrecio());
        }
        if(dto.getStockMinimo() != null){
            entity.setStockMinimo(dto.getStockMinimo());
        }
    }

    public static NivelStock calcularNivelStock(Integer stockActual, Integer stockMinimo){
        if(stockActual == null || stockActual ==0){
            return NivelStock.SIN_STOCK;
        }
        if(stockActual <= stockMinimo){
            return NivelStock.BAJO;
        }
        if(stockActual > stockMinimo * 2){
            return NivelStock.ALTO;
        }
        return NivelStock.NORMAL;
    }
}
