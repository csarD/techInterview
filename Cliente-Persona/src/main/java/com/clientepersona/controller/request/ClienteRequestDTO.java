package com.clientepersona.controller.request;

import com.clientepersona.util.stringNormalized.StringNormalized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Data
public class ClienteRequestDTO {

    @NonNull
    private Integer id;
    @NonNull
    @StringNormalized
    private String contrasena;

    @NonNull
    private Integer estado;

    @NonNull
    @StringNormalized
    private String personaid;

    public ClienteRequestDTO(){}
}
