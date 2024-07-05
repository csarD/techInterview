package com.securityms.repository;

import com.securityms.repository.domain.RelacionRolPrivilegio;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelacionRolPrivilegioRepository extends
    JpaRepository<RelacionRolPrivilegio, Integer> {

  List<RelacionRolPrivilegio> findByIdRol(Integer idRol);

  Page<RelacionRolPrivilegio> findByIdRol(Integer idRol, Pageable pageable);

  @Transactional
  void deleteByIdPrivilegioAndIdRol(Integer idPrivilegio, Integer idRol);

  @Transactional
  void deleteByIdRol(Integer idRol);
}
