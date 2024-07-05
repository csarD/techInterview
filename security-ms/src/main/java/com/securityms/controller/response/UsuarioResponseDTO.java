package com.securityms.controller.response;

import com.securityms.repository.domain.TipoDoc;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data @RequiredArgsConstructor
public class UsuarioResponseDTO {

    @NonNull
    private Integer id;
    @NonNull
    private String primerNombre;
    @NonNull
    private String primerApellido;
    @NonNull
    private String usuario;
    @NonNull
    private String numeroIdentifiacion;
    @NonNull
    private String email;
    @NonNull
    private TipoDoc tipoDoc;

    private Integer idRole;
    private String usuarioInserccion;
    private Instant fechaInserccion;
    private String segundoNombre;
    private String segundoApellido;
    private String nombreRol;

    private boolean estado;


    public UsuarioResponseDTO(){}

}
