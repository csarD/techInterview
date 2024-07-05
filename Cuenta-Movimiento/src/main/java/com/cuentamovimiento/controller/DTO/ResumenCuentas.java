package com.cuentamovimiento.controller.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor

public class ResumenCuentas {

    public Integer NumeroCuenta;
    public Double SaldoActual;
}
