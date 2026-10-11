package com.grupo7.GestionInventarioAPI.model;

import com.grupo7.GestionInventarioAPI.model.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 150)
    private String nombreUsuario;

    @Column(name = "apellido_usuario", nullable = false, length = 150)
    private String apellidoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_usuario", nullable = false, length = 20)
    private RolUsuario rolUsuario;

    @Builder.Default
    @Column(name = "activa", nullable = false)
    private boolean activa = true;
}
