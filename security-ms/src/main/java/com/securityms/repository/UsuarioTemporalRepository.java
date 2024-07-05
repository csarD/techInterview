package com.securityms.repository;

import com.securityms.repository.domain.UsuarioTemporal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioTemporalRepository extends JpaRepository<UsuarioTemporal, Integer> {

    Optional<UsuarioTemporal> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);

    @Modifying
    @Query("DELETE FROM UsuarioTemporal u WHERE u.fechaInsercion < ?1")
    void deleteBeforeFechaInsercion(LocalDateTime fechaInsercion);
}
