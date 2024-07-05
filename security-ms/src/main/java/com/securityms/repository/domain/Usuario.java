package com.securityms.repository.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario", nullable = false)
  private Integer id;

  @Column(name = "primerNombre", length = 50)
  private String primerNombre;

  @Column(name = "segundoNombre", length = 50)
  private String segundoNombre;

  @Column(name = "primerApellido", length = 50)
  private String primerApellido;

  @Column(name = "segundoApellido", length = 50)
  private String segundoApellido;

  @Column(name = "usuario", length = 30, unique = true)
  private String usuario;

  @Column(name = "password", length = 64)
  private String password;

  @Column(name = "email", length = 60, nullable = false)
  private String email;

  @Column(name = "usuario_inserccion", length = 50)
  private String usuarioInserccion;

  @Column(name = "fecha_inserccion")
  private Instant fechaInserccion;

  @Column(name = "estado")
  private Boolean estado = true;

  @Column(name = "password_updated", nullable = false)
  private Boolean passwordUpdated = false;

  @Column(name = "id_rol")
  private Integer idRole;

  @OneToMany(mappedBy = "idUsuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<TipoIdentifiacion> tiposIdentificacion = new ArrayList<>();

  @Column(name = "password_update_code", length = 6)
  private String passwordUpdateCode;

  @Column(name = "password_update_code_validated", nullable = false)
  private Boolean passwordUpdateCodeValidated = false;

  @Column(name = "password_update_code_time")
  private LocalDateTime passwordUpdateCodeTime;

  @Transient
  private String nombreRol;

  public Usuario() {
  }

}