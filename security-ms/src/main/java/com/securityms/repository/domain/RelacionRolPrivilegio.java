package com.securityms.repository.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Entity
@Table(name = "relacion_rol_privilegio")
@Data
@RequiredArgsConstructor @AllArgsConstructor
public class RelacionRolPrivilegio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_relacion_rol_privilegio", nullable = false)
  private Integer id;
  @NonNull
  @Column(name = "id_rol", nullable = false)
  private Integer idRol;
  @NonNull
  @Column(name = "id_privilegio", nullable = false)
  private Integer idPrivilegio;

  public RelacionRolPrivilegio() {
  }
}
