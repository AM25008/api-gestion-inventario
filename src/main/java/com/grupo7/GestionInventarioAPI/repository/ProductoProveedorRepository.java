package com.grupo7.GestionInventarioAPI.repository;

import com.grupo7.GestionInventarioAPI.model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer> {

    List<ProductoProveedor> findByProducto_IdProducto(Integer idProducto);

    List<ProductoProveedor> findByProveedor_IdProveedor(Integer idProveedor);

    Optional<ProductoProveedor> findByProducto_IdProductoAndProveedor_IdProveedor(Integer idProducto, Integer idProveedor);

    boolean existsByProducto_IdProductoAndProveedor_IdProveedor(Integer idProducto, Integer idProveedor);
}
