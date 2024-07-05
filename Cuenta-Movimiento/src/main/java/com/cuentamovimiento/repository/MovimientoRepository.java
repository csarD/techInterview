package com.cuentamovimiento.repository;

import com.cuentamovimiento.repository.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    List<Movimiento> findMovimientoByCuenta(Integer cuenta);
}
