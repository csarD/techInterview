package com.securityms.controller.request;

import com.securityms.util.stringNormalized.StringNormalized;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class RoleRequestDTO {

    private Integer id;

    @StringNormalized
    @NonNull
    private String nombreRol;

    @StringNormalized
    @NonNull
    private String descripcion;
    private Boolean estado = true;

    public RoleRequestDTO(){}

}
