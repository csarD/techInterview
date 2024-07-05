package com.clientepersona.service;

import com.clientepersona.controller.request.PersonaRequestDTO;
import com.clientepersona.repository.domain.Persona;

import java.util.List;

public interface IPersonaService {
    Persona get(String id);
    String delete(PersonaRequestDTO id);
    Persona save(PersonaRequestDTO request);
    Persona update(PersonaRequestDTO request);
    List<Persona> findAll();


}
