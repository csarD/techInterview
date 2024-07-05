package com.cuentamovimiento.service;

import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.repository.domain.Cuenta;

import java.util.List;

public interface ICuentaService {
    Cuenta get(Integer id);
    List<Integer> getAllClientAccounts(int idClient);
    String delete(CuentaRequestDTO id);
    Cuenta save(CuentaRequestDTO request);
    Cuenta update(CuentaRequestDTO request);
    List<Cuenta> findAll();


}
