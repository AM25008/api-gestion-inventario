package com.grupo7.GestionInventarioAPI.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Colum(name= "id_categoria")
    private Integer idCategoria;

    @Column(name= "nombre", nullable = false, length = 150)
    private String nombreCategoria;

    @Column(name= "descripcion", length = 200)
    private String descripcion;

    @Builder.Default
    @Column(name= "activa", nullable = false)
    private boolean activa = true;

}
