package com.cuentamovimiento.service.implementation;


import com.cuentamovimiento.controller.request.MovimientoRequestDTO;
import com.cuentamovimiento.repository.CuentaRepository;
import com.cuentamovimiento.repository.MovimientoRepository;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.repository.domain.Movimiento;
import com.cuentamovimiento.service.IMovimientoService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

  private final Logger logger = LogManager.getLogger(MovimientoServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private MovimientoRepository movimientoRepository;
  @Autowired
  private CuentaRepository cuentaRepository;

  @Override
  public Movimiento get(Integer id) {
    try {
      logger.info("Get element in "+this.getClass().getSimpleName()+"with id "+id);

      Movimiento prueba = movimientoRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("No existe producto con el id: " + id));

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
  public List<Movimiento> getAllMovesfromAccount(Integer account) {
    return movimientoRepository.findMovimientoByCuenta(account);
  }

  @Override
  public String delete(MovimientoRequestDTO id) {
    try {
      logger.info("Delete element in "+this.getClass().getSimpleName()+"with id "+id.getId());
      Movimiento prueba = modelMapper.map(id, Movimiento.class);
      List<Movimiento>before=movimientoRepository.findAll();
      movimientoRepository.delete(prueba);
      //agregar update del saldo
      List<Movimiento>after=movimientoRepository.findAll();
      if(before.size()>after.size()){
        return "Movimiento Eliminado con exito";
      }else{
        return "No se encontro Movimiento a eliminar";
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
  public String save(MovimientoRequestDTO request) {
    try {

      logger.info("Create element in "+this.getClass().getSimpleName());

      Movimiento prueba = modelMapper.map(request, Movimiento.class);
      //agregar validacion y update de saldo

      Cuenta info = cuentaRepository.findById(request.getCuenta()).orElseThrow(
              () -> new EntityNotFoundException("Cuenta inexistente, movimiento no registrado"));


      Double saldo=info.getSaldo();

      if(prueba.getValor()>0){
        prueba.setSaldo(saldo+ prueba.getValor());
        info.setSaldo(prueba.getSaldo());
        cuentaRepository.save(info);
        movimientoRepository.save(prueba);
        return "Movimiento generado exitosamente";
      }
      Double newSaldo=saldo+prueba.getValor();
      if(newSaldo>0){
        prueba.setSaldo(newSaldo);
        info.setSaldo(newSaldo);
        cuentaRepository.save(info);
        movimientoRepository.save(prueba);
        return "Movimiento generado exitosamente";
      }
      return "Saldo insuficiente";


    } catch (Exception e) {
      String msg= "Error while save in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public Movimiento update(MovimientoRequestDTO request) {
    try {
      logger.info("Update element in "+this.getClass().getSimpleName());

      Movimiento prueba = movimientoRepository.findById(request.getId())
          .orElseThrow(() -> new EntityNotFoundException("Movimiento not found"));



      return movimientoRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while update in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public List<Movimiento> findAll() {
    try{
      logger.info("Get all elements in "+this.getClass().getSimpleName());

      return movimientoRepository.findAll();
    } catch (Exception e) {
      String msg= "Error getting all elements in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
    logger.error(msg);
    throw new RuntimeException(msg.split("TRACE:")[0], e);
  }

  }

}
