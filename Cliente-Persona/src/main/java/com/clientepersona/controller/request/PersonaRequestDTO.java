package com.clientepersona.controller.request;

import com.clientepersona.util.stringNormalized.StringNormalized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Data
public class PersonaRequestDTO {

    @NonNull
    @StringNormalized
    private String identificacion;
    @NonNull
    @StringNormalized
    private String nombre;

    @NonNull
    @StringNormalized
    private String genero;

    @NonNull
    @StringNormalized
    private String direccion;
    @NonNull
    private Integer telefono;
    @NonNull
    private Integer edad;


    public PersonaRequestDTO(){}
}
