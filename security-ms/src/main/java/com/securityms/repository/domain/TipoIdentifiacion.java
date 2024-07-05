package com.securityms.repository.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_identifiacion")
@AllArgsConstructor
@Getter
@Setter
public class TipoIdentifiacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idTipoIdentifiacion", nullable = false)
  private Integer id;

  @Column(name = "numeroIdentifiacion", length = 50, unique = true)
  private String numeroIdentifiacion;

  @Column(name = "estado")
  private Boolean estado;

  @Column(name = "pais", length = 2)
  private String pais;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "idtipo_doc")
  private TipoDoc idtipoDoc;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_usuario")
  private Usuario idUsuario;

  public TipoIdentifiacion() {
  }

}