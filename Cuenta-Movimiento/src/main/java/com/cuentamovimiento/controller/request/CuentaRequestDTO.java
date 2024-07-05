package com.cuentamovimiento.controller.request;

import com.cuentamovimiento.util.stringNormalized.StringNormalized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Data
public class CuentaRequestDTO {

    @NonNull
    private Integer numero;


    @NonNull
    private Integer tipo;

    @NonNull
    private Double saldo;

    @NonNull
    private Integer estado;

    @NonNull
    private Integer cliente;


    public CuentaRequestDTO(){}
}
