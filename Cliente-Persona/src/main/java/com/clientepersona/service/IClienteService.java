package com.clientepersona.service;

import com.clientepersona.controller.request.ClienteRequestDTO;
import com.clientepersona.repository.domain.Cliente;

import java.util.List;

public interface IClienteService {
    Cliente get(String id);
    String delete(ClienteRequestDTO id);
    Cliente save(ClienteRequestDTO request);
    Cliente update(ClienteRequestDTO request);
    List<Cliente> findAll();


}
