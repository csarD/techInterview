package com.securityms.repository.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_rol", nullable = false)
  private Integer id;

  @Column(name = "nombreRol", length = 50, unique = true)
  private String nombreRol;

  @Column(name = "descripcion", length = 100)
  private String descripcion;

  @Column(name = "usuarioInserccion", length = 50)
  private String usuarioInserccion;

  @Column(name = "fechaInserccion")
  private Instant fechaInserccion;

  @Column(name = "estado")
  private Boolean estado;

  public Role() {
  }

}