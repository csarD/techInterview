package com.securityms.service;

import com.securityms.controller.request.RoleRequestDTO;
import com.securityms.controller.response.RoleResponseDTO;

import java.util.List;

public interface IRoleService {

    List<RoleResponseDTO> findAll();

    RoleResponseDTO saveRole(RoleRequestDTO role);

    RoleResponseDTO findById(Integer id);

    RoleResponseDTO updateRole(RoleRequestDTO role);

    void delete(Integer id);
}
