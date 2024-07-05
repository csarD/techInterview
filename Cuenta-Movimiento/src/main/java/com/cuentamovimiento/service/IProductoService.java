package com.cuentamovimiento.service;

import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.controller.request.ProductoRequestDTO;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.repository.domain.Producto;

import java.util.List;

public interface IProductoService {
    Producto get(Integer id);
    String delete(ProductoRequestDTO id);
    Producto save(ProductoRequestDTO request);
    Producto update(ProductoRequestDTO request);
    List<Producto> findAll();


}
