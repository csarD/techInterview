package com.cuentamovimiento.repository;

import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.repository.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
