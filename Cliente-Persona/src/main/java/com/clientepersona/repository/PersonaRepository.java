package com.clientepersona.repository;

import com.clientepersona.repository.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByIdentificacion(String identifier);
}
