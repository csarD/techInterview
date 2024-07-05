package com.cuentamovimiento.controller.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {
    public Instant fecha;
    public String NombreCliente;
    public Integer NumeroCuenta;
    public String TipoProducto;
    public Double SaldoInicial;
    public Integer Status;
    public Double Movimiento;
    public Double SaldoActual;
}
