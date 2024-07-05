package com.securityms.repository.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios_temporal")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_temporal", nullable = false)
    private Integer id;

    @Column(name = "usuario", length = 30, unique = true)
    private String usuario;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "fecha_inserccion")
    private LocalDateTime fechaInsercion;

}