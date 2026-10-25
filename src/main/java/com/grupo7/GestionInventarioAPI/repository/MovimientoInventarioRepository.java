package com.grupo7.GestionInventarioAPI.repository;

import com.grupo7.GestionInventarioAPI.model.MovimientoInventario;
import com.grupo7.GestionInventarioAPI.model.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer> {

    List<MovimientoInventario> findByProducto_IdProducto(Integer idProducto);

    List<MovimientoInventario> findByUsuario_IdUsuario(Integer idProducto);

    List<MovimientoInventario> findByTipoMovimiento(TipoMovimiento tipoMovimiento);
}
