package com.clientepersona.service.implementation;

import com.clientepersona.controller.request.ClienteRequestDTO;
import com.clientepersona.repository.ClienteRepository;

import com.clientepersona.repository.PersonaRepository;
import com.clientepersona.repository.domain.Cliente;
import com.clientepersona.repository.domain.Persona;
import com.clientepersona.service.IClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

  private final Logger logger = LogManager.getLogger(ClienteServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private ClienteRepository clienteRepository;
  @Autowired
  private PersonaRepository personaRepository;
  @Override
  public Cliente get(String id) {
    try {
      logger.info("Get element in "+this.getClass().getSimpleName()+"with id "+id);

      Cliente prueba = clienteRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("No existe cliente con el id: " + id));

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
  public String delete(ClienteRequestDTO id) {
    try {
      logger.info("Delete element in "+this.getClass().getSimpleName()+"with id "+id.getId());
      Cliente prueba = modelMapper.map(id, Cliente.class);
      List<Cliente>before=clienteRepository.findAll();
      clienteRepository.delete(prueba);
      List<Cliente>after=clienteRepository.findAll();
      if(before.size()>after.size()){
        return "Cliente Eliminada con exito";
      }else{
        return "No se encontro cliente a eliminar";
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
  public Cliente save(ClienteRequestDTO request) {
    try {

      logger.info("Create element in "+this.getClass().getSimpleName());

      Cliente prueba = modelMapper.map(request, Cliente.class);
      Persona info = personaRepository.findByIdentificacion(request.getPersonaid())
              .orElseThrow(() -> new EntityNotFoundException("No existe persona con el id: " + request.getPersonaid()));
      prueba.setPersonaid(info);
      return clienteRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while save in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public Cliente update(ClienteRequestDTO request) {
    try {
      logger.info("Update element in "+this.getClass().getSimpleName());

      Cliente prueba = clienteRepository.findById(request.getId())
          .orElseThrow(() -> new EntityNotFoundException("Persona not found"));

      prueba.setEstado(request.getEstado());
      //TODO: hash the password
      prueba.setContrasena(request.getContrasena());
      return clienteRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while update in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public List<Cliente> findAll() {
    try{
      logger.info("Get all elements in "+this.getClass().getSimpleName());

      return clienteRepository.findAll();
    } catch (Exception e) {
      String msg= "Error getting all elements in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
    logger.error(msg);
    throw new RuntimeException(msg.split("TRACE:")[0], e);
  }

  }

}
