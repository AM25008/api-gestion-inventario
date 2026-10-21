package com.grupo7.GestionInventarioAPI.repository;

import com.grupo7.GestionInventarioAPI.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    List<Producto> findByActivoTrue();
    Optional<Producto> findByIdProductoAndActivoTrue(Integer idProducto);
    List<Producto> findByCategoria_IdCategoriaAndActivoTrue(Integer idCategoria);

    boolean existsByNombreProducto(String nombreProducto);

    List<Producto> findByNombreProductoContainingIgnoreCaseAndActiveTrue(String nombreProducto);
    @Query("SELECT  p FROM Producto p WHERE p.activo = true AND p.stockActual <= p.stockMinimo") List<Producto> findProductosConStockBajo();
}
