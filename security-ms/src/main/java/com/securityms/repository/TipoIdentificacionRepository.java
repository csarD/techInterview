package com.securityms.repository;

import com.securityms.repository.domain.TipoIdentifiacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoIdentificacionRepository extends JpaRepository<TipoIdentifiacion, Integer> {

    Optional<TipoIdentifiacion> findByNumeroIdentifiacion(String numeroIdentifiacion);
}
