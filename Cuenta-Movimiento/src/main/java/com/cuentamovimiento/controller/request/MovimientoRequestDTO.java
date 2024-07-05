package com.cuentamovimiento.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
public class MovimientoRequestDTO {

    @NonNull
    private Integer id;


    @NonNull
    private Integer cuenta;

    @NonNull
    private Double valor;

    @NonNull
    private Instant fecha;

    @NonNull
    private Double saldo;


    public MovimientoRequestDTO(){}
}
