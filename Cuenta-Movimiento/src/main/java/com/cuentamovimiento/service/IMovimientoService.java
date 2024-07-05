package com.cuentamovimiento.service;

import com.cuentamovimiento.controller.request.MovimientoRequestDTO;
import com.cuentamovimiento.repository.domain.Movimiento;

import java.util.List;

public interface IMovimientoService {
    Movimiento get(Integer id);
    List<Movimiento> getAllMovesfromAccount(Integer account);
    String delete(MovimientoRequestDTO id);
    String save(MovimientoRequestDTO request);
    Movimiento update(MovimientoRequestDTO request);
    List<Movimiento> findAll();


}
