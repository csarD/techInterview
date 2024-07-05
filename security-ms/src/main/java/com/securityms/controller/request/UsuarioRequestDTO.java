package com.securityms.controller.request;

import com.securityms.util.stringNormalized.StringNormalized;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class UsuarioRequestDTO {

    private Integer id;
    @StringNormalized
    @NonNull
    private String primerNombre;
    @StringNormalized
    @NonNull
    private String primerApellido;
    @StringNormalized
    @NonNull
    private String usuario;
    @StringNormalized
    @NonNull
    private String email;
    @NonNull
    @StringNormalized
    private String numeroIdentifiacion;
    @NonNull
    private Integer idTipoDoc;

    @StringNormalized
    private String segundoNombre;
    @StringNormalized
    private String segundoApellido;
    private boolean becario = false;
    private boolean facilitador = false;

    @StringNormalized
    private String beca;

    private boolean estado;

    public UsuarioRequestDTO(){}
}
