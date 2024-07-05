package com.cuentamovimiento.service.implementation;


import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.controller.request.ProductoRequestDTO;
import com.cuentamovimiento.repository.CuentaRepository;
import com.cuentamovimiento.repository.ProductoRepository;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.repository.domain.Producto;
import com.cuentamovimiento.service.ICuentaService;
import com.cuentamovimiento.service.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

  private final Logger logger = LogManager.getLogger(ProductoServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private ProductoRepository productoRepository;

  @Override
  public Producto get(Integer id) {
    try {
      logger.info("Get element in "+this.getClass().getSimpleName()+"with id "+id);

      Producto prueba = productoRepository.findById(id)
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
  public String delete(ProductoRequestDTO id) {
    try {
      logger.info("Delete element in "+this.getClass().getSimpleName()+"with id "+id.getId());
      Producto prueba = modelMapper.map(id, Producto.class);
      List<Producto>before=productoRepository.findAll();
      productoRepository.delete(prueba);
      List<Producto>after=productoRepository.findAll();
      if(before.size()>after.size()){
        return "Producto Eliminada con exito";
      }else{
        return "No se encontro Producto a eliminar";
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
  public Producto save(ProductoRequestDTO request) {
    try {

      logger.info("Create element in "+this.getClass().getSimpleName());

      Producto prueba = modelMapper.map(request, Producto.class);

      return productoRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while save in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public Producto update(ProductoRequestDTO request) {
    try {
      logger.info("Update element in "+this.getClass().getSimpleName());

      Producto prueba = productoRepository.findById(request.getId())
          .orElseThrow(() -> new EntityNotFoundException("Producto not found"));

      prueba.setActive(request.getActive());
      prueba.setNombre(request.getNombre());

      return productoRepository.save(prueba);
    } catch (Exception e) {
      String msg= "Error while update in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
      logger.error(msg);
      throw new RuntimeException(msg.split("TRACE:")[0], e);
    }
  }


  @Override
  public List<Producto> findAll() {
    try{
      logger.info("Get all elements in "+this.getClass().getSimpleName());

      return productoRepository.findAll();
    } catch (Exception e) {
      String msg= "Error getting all elements in "+this.getClass().getSimpleName()+"TRACE:"+e.getMessage();
    logger.error(msg);
    throw new RuntimeException(msg.split("TRACE:")[0], e);
  }

  }

}
