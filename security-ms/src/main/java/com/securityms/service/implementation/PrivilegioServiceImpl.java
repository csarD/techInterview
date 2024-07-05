package com.securityms.service.implementation;

import com.securityms.controller.request.PrivilegioRequestDTO;
import com.securityms.controller.response.RoleResponseDTO;
import com.securityms.repository.PrivilegioRepository;
import com.securityms.repository.RelacionRolPrivilegioRepository;
import com.securityms.repository.domain.Privilegio;
import com.securityms.repository.domain.RelacionRolPrivilegio;
import com.securityms.service.IPrivilegioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PrivilegioServiceImpl implements IPrivilegioService {

  private final Logger logger = LogManager.getLogger(PrivilegioServiceImpl.class);

  @Autowired
  private RelacionRolPrivilegioRepository relacionRolPrivilegioRepository;
  @Autowired
  private PrivilegioRepository repository;
  @Autowired
  private RoleServiceImpl roleService;

  @Override
  public List<Privilegio> findAll() {
    return repository.findAll();
  }

  @Override
  public List<String> getPrivilegesStringList(Integer idRol) {
    List<String> nameList = new ArrayList<>();
    try {
      for (RelacionRolPrivilegio element : relacionRolPrivilegioRepository.findByIdRol(idRol)) {
        Optional<Privilegio> privilegio = repository.findById(element.getIdPrivilegio());
        if (privilegio.isEmpty()) {
          logger.error("No privilegio found for this id: " + element.getIdPrivilegio());
        }
        nameList.add(repository.findById(element.getIdPrivilegio()).get().getNombre());
      }
    } catch (Exception e) {
      logger.error("Error while getting privileges for role with ID " + idRol, e);
      throw new RuntimeException("Error while getting privileges for role with ID " + idRol, e);
    }
    return nameList;
  }


  @Override
  public List<Privilegio> findAllByRol(Integer idRol) {
    List<Privilegio> responseList = new ArrayList<>();
    try {
      for (RelacionRolPrivilegio element : relacionRolPrivilegioRepository.findByIdRol(idRol)) {
        Optional<Privilegio> privilegio = repository.findById(element.getIdPrivilegio());
        if (privilegio.isEmpty()) {
          logger.error("No privilegio found for this id: " + element.getIdPrivilegio());
        }
        responseList.add(repository.findById(element.getIdPrivilegio()).get());
      }
    } catch (Exception e) {
      logger.error("Error in findAllByRol: " + e.getMessage());
      throw new RuntimeException("Error in findAllByRol: " + e.getMessage());
    }
    return responseList;
  }

  public Page<Privilegio> findAllByRol(Integer idRol, Pageable pageable) {
    List<Privilegio> responseList = new ArrayList<>();
    Page<RelacionRolPrivilegio> relacionRolPrivilegioList = relacionRolPrivilegioRepository.findByIdRol(idRol, pageable);
    try {
      for (RelacionRolPrivilegio element : relacionRolPrivilegioList) {
        Optional<Privilegio> privilegio = repository.findById(element.getIdPrivilegio());
        if (privilegio.isEmpty()) {
          logger.error("No privilegio found for this id: " + element.getIdPrivilegio());
        }
        responseList.add(repository.findById(element.getIdPrivilegio()).get());
      }
    } catch (Exception e) {
      logger.error("Error in findAllByRol: " + e.getMessage());
      throw new RuntimeException("Error in findAllByRol: " + e.getMessage());
    }
    return new PageImpl<>(responseList, pageable, relacionRolPrivilegioList.getTotalElements());
  }


  @Override
  public void addPrivileges(PrivilegioRequestDTO request) {
    RoleResponseDTO role = roleService.findById(request.getIdRol());
    roleService.validateRoleName(role.getNombreRol());
    for (Integer idPrivilegio : request.getIdPrivilegioList()) {
      repository.findById(idPrivilegio)
          .orElseThrow(() -> new EntityNotFoundException("El privilegio no existe"));
      relacionRolPrivilegioRepository.save(
          new RelacionRolPrivilegio(request.getIdRol(), idPrivilegio));
    }
  }

  @Override
  public void removePrivileges(PrivilegioRequestDTO request) {
    RoleResponseDTO role = roleService.findById(request.getIdRol());
    roleService.validateRoleName(role.getNombreRol());
    for (Integer idPrivilegio : request.getIdPrivilegioList()) {
      relacionRolPrivilegioRepository.deleteByIdPrivilegioAndIdRol(idPrivilegio,
          request.getIdRol());
    }
  }

  @Override
  public void removePrivilegesByRol(Integer idRol) {
    relacionRolPrivilegioRepository.deleteByIdRol(idRol);
  }
}
