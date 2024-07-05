package com.clientepersona.service.implementation;

import com.clientepersona.controller.request.PersonaRequestDTO;
import com.clientepersona.repository.PersonaRepository;
import com.clientepersona.repository.domain.Persona;
import com.clientepersona.service.IPersonaService;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl implements IPersonaService {

  private final Logger logger = LogManager.getLogger(PersonaServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private PersonaRepository personaRepository;

  @Override
  public Persona get(String id) {
    try {
      logger.info("Get element in "+this.getClass().getSimpleName()+"with id "+id);

      Persona prueba = personaRepository.findByIdentificacion(id)
              .orElseThrow(() -> new EntityNotFoundException("No existe persona con el id: " + id));

      return prueba;
    }catch (EntityNotFoundException e){
      throw e;
    }
    catch (Exception e) {
      String msg= "Error while get in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }

  @Override
  public String delete(PersonaRequestDTO id) {
    try {
      logger.info("Delete element in "+this.getClass().getSimpleName()+"with id "+id.getIdentificacion());
      Persona prueba = modelMapper.map(id, Persona.class);
      List<Persona>before=personaRepository.findAll();
      personaRepository.delete(prueba);
      List<Persona>after=personaRepository.findAll();
      if(before.size()>after.size()){
        return "Persona Eliminada con exito";
      }else{
        return "No se encontro persona a eliminar";
      }



    }catch (EntityNotFoundException e){
      throw e;
    }
    catch (Exception e) {
      String msg= "Error while delete in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }

  @Override
  public Persona save(PersonaRequestDTO request) {
    try {

      logger.info("Create element in "+this.getClass().getSimpleName());

      Persona prueba = modelMapper.map(request, Persona.class);

      logger.info("Create new prueba automatica: " + prueba);
      return personaRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while save in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public Persona update(PersonaRequestDTO request) {
    try {
      logger.info("Update element in "+this.getClass().getSimpleName());

      Persona prueba = personaRepository.findByIdentificacion(request.getIdentificacion())
          .orElseThrow(() -> new EntityNotFoundException("Persona not found"));

      prueba.setDireccion(request.getDireccion());
      prueba.setEdad(request.getEdad());
      prueba.setGenero(request.getGenero());
      prueba.setTelefono(request.getTelefono());

      return personaRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while update in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public List<Persona> findAll() {
    try{
      logger.info("Get all elements in "+this.getClass().getSimpleName());

      return personaRepository.findAll();
    } catch (Exception e) {
      String msg= "Error getting all elements in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
    logger.error(msg);
    throw new RuntimeException(msg.split("TRACE:")[0], e);
  }

  }

}
