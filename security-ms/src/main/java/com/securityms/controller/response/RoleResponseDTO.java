package com.securityms.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
public class RoleResponseDTO {

    @NonNull
    private Integer id;

    @NonNull
    private String nombreRol;

    @NonNull
    private String descripcion;

    private String usuarioInserccion;

    @NonNull
    private Instant fechaInserccion;

    @NonNull
    private Boolean estado;

    public  RoleResponseDTO(){}

}
