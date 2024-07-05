package com.securityms.controller.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.securityms.repository.enums.EstadoPromocion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class PromocionResponseDTO {

    private Integer id;

    private Integer idBeca;

    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDateTime fechaInsercion;

    private String urlImagen;

    private String usuarioInsercion;

    @Enumerated(EnumType.STRING)
    private EstadoPromocion estado;

    private String idFormulario;

    @JsonIgnore
    private List<Object> fases;

    @JsonIgnore
    private Set<Object> requisitos;
    private Boolean publica;
}
