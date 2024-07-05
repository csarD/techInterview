package com.cuentamovimiento.repository;

import com.cuentamovimiento.repository.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    @Query(value = "select numero\n" +
            "from cuenta \n" +
            "where cuenta.cliente = ?1\n" +
            " ", nativeQuery = true)
    List<Integer> getClientAccount(int idClient);
}
