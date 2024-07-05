package com.securityms.service;

import com.securityms.controller.request.PrivilegioRequestDTO;
import com.securityms.repository.domain.Privilegio;

import java.util.List;

public interface IPrivilegioService {

    List<Privilegio> findAll();
    List<String> getPrivilegesStringList(Integer idRol);
    List<Privilegio> findAllByRol(Integer idRol);
    void addPrivileges(PrivilegioRequestDTO request);
    void removePrivileges(PrivilegioRequestDTO request);
    void removePrivilegesByRol(Integer idRol);
}
