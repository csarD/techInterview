package com.cuentamovimiento.controller.request;

import com.cuentamovimiento.util.stringNormalized.StringNormalized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Data
public class ProductoRequestDTO {

    @NonNull
    private Integer id;
    @NonNull
    @StringNormalized
    private String nombre;

    @NonNull
    private Integer active;


    public ProductoRequestDTO(){}
}
