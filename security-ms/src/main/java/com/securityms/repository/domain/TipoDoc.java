package com.securityms.repository.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Table(name = "tipo_doc")
@AllArgsConstructor
@Getter
@Setter
public class TipoDoc {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idtipo_doc", nullable = false)
  private Integer id;

  @Column(name = "nombre", length = 45)
  private String nombre;

  @Column(name = "estado")
  private Boolean estado;

  @Column(name = "mascaraDoc", length = 20)
  private String mascaraDoc;

  @OneToMany(mappedBy = "idtipoDoc")
  @JsonIgnore
  private Set<TipoIdentifiacion> tipoIdentifiacions = new LinkedHashSet<>();

  public TipoDoc() {
  }

}