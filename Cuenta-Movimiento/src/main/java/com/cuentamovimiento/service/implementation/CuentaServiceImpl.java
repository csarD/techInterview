package com.cuentamovimiento.service.implementation;


import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.repository.CuentaRepository;
import com.cuentamovimiento.repository.ProductoRepository;
import com.cuentamovimiento.repository.domain.Cliente;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.service.ICuentaService;
import com.cuentamovimiento.service.feignClient.ClientFeight;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements ICuentaService {

  private final Logger logger = LogManager.getLogger(CuentaServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private CuentaRepository cuentaRepository;
  @Autowired
  private ProductoRepository proRepository;
@Autowired
private ClientFeight ClientInfo;
  @Override
  public Cuenta get(Integer id) {
    try {
      logger.info("Get element in "+this.getClass().getSimpleName()+"with id "+id);

      Cuenta prueba = cuentaRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("No existe cuenta con el id: " + id));

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
  public List<Integer> getAllClientAccounts(int idClient) {
    return cuentaRepository.getClientAccount(idClient);
  }

  @Override
  public String delete(CuentaRequestDTO id) {
    try {
      logger.info("Delete element in "+this.getClass().getSimpleName()+"with id "+id.getNumero());
      Cuenta prueba = modelMapper.map(id, Cuenta.class);
      List<Cuenta>before=cuentaRepository.findAll();
      cuentaRepository.delete(prueba);
      List<Cuenta>after=cuentaRepository.findAll();
      if(before.size()>after.size()){
        return "Cuenta Eliminada con exito";
      }else{
        return "No se encontro Cuenta a eliminar";
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
  public Cuenta save(CuentaRequestDTO request) {
    try {

      logger.info("Create element in "+this.getClass().getSimpleName());

      Cuenta prueba = modelMapper.map(request, Cuenta.class);
      prueba.setId(request.getNumero());

      Cliente dato= modelMapper.map(ClientInfo.obtenerCliente(request.getCliente()).get("payload"),Cliente.class);
      prueba.setCliente(dato);
      return cuentaRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while save in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public Cuenta update(CuentaRequestDTO request) {
    try {
      logger.info("Update element in "+this.getClass().getSimpleName());

      Cuenta prueba = cuentaRepository.findById(request.getNumero())
          .orElseThrow(() -> new EntityNotFoundException("Cuenta not found"));
      //el resto de la request se ignora porque el saldo se modifica con los movimientos
      prueba.setEstado(request.getEstado());

      return cuentaRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while update in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public List<Cuenta> findAll() {
    try{
      logger.info("Get all elements in "+this.getClass().getSimpleName());

      return cuentaRepository.findAll();
    } catch (Exception e) {
      String msg= "Error getting all elements in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
    logger.error(msg);
    throw new RuntimeException(msg.split("TRACE:")[0], e);
  }

  }

}
