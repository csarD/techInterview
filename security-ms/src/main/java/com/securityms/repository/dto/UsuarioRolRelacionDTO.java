package com.securityms.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data @AllArgsConstructor
public class UsuarioRolRelacionDTO {

    @NonNull
    private Integer idUsuario;

    @NonNull
    private Integer idRol;

    public UsuarioRolRelacionDTO(){}

}
