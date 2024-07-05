package com.securityms.service.implementation;

import com.securityms.controller.request.RoleRequestDTO;
import com.securityms.controller.response.RoleResponseDTO;
import com.securityms.repository.RoleRepository;
import com.securityms.repository.domain.Role;
import com.securityms.service.IRoleService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

  private final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private RoleRepository repository;
  @Autowired
  private PrivilegioServiceImpl privilegioService;

  @Override
  public List<RoleResponseDTO> findAll() {
    try {
      return repository.findAll().stream()
          .map(role -> modelMapper.map(role, RoleResponseDTO.class))
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Error during findAll(): " + e.getMessage());
      return Collections.emptyList();
    }
  }


  @Override
  public RoleResponseDTO saveRole(RoleRequestDTO request) {
    logger.info("Save new role: " + request);
    Role role = modelMapper.map(request, Role.class);
    role.setFechaInserccion(Instant.now());
    return modelMapper.map(repository.save(role), RoleResponseDTO.class);
  }

  @Override
  public RoleResponseDTO findById(Integer id) {
    Role role = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Role not found."));
    return modelMapper.map(role, RoleResponseDTO.class);
  }

  @Override
  public RoleResponseDTO updateRole(RoleRequestDTO request) {
    logger.info("Update role: " + request);
    Role role = repository.findById(request.getId())
        .orElseThrow(() -> new EntityNotFoundException("Role not found."));

    validateRoleName(role.getNombreRol());
    role.setNombreRol(request.getNombreRol());
    role.setDescripcion(request.getDescripcion());
    role.setEstado(request.getEstado());
    return modelMapper.map(repository.save(role), RoleResponseDTO.class);
  }

  @Override
  public void delete(Integer id) {
    RoleResponseDTO role = findById(id);
    validateRoleName(role.getNombreRol());
    privilegioService.removePrivilegesByRol(id);
    repository.deleteById(id);
  }

  public void validateRoleName(String name) {
    if (name.equals("Administrador") || name.equals("Prebecado") || name.equals("Postbecado")) {
      throw new EntityExistsException("No se puede borrar o editar este rol");
    }
  }

  public RoleResponseDTO getBecarioRole() {
    logger.info("Get Becario role");
    Role role = repository.findByNombreRol("Becario")
        .orElseThrow(() -> new EntityNotFoundException("Role not found."));
    return modelMapper.map(role, RoleResponseDTO.class);
  }

  public RoleResponseDTO getFacilitadorRole() {
    logger.info("Get Facilitador role");
    Role role = repository.findByNombreRol("Facilitador")
        .orElseThrow(() -> new EntityNotFoundException("Role not found."));
    return modelMapper.map(role, RoleResponseDTO.class);
  }

}
