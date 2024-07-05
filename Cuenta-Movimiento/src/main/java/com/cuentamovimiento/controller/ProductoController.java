package com.cuentamovimiento.controller;

import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.controller.request.ProductoRequestDTO;
import com.cuentamovimiento.controller.response.BaseResponse;
import com.cuentamovimiento.controller.response.Response;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.repository.domain.Producto;
import com.cuentamovimiento.service.implementation.CuentaServiceImpl;
import com.cuentamovimiento.service.implementation.ProductoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

  @Autowired
  private ProductoServiceImpl service;

  @GetMapping("/")
  @Operation(summary = "Obtener producto segun id")
  public ResponseEntity<Response<Producto>> PersonInfo(

          @RequestParam  Integer request) {

    Producto persona = service.get(request);
    BaseResponse<Producto> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cuenta encontrada ",
            persona);
  }

  @PostMapping()
  @Operation(summary = "Nuevo Producto")
  public ResponseEntity<Response<Producto>> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de Producto a guardar")
      @RequestBody @Valid ProductoRequestDTO request) {


    Producto persona = service.save(request);
    BaseResponse<Producto> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Producto guardada exitosamente",
            persona);
  }


  @PutMapping()
  @Operation(summary = "Actualizar Produto")
  public ResponseEntity<Response<Producto>> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de producto a actualizar")
      @RequestBody @Valid ProductoRequestDTO request) {
    Producto persona = service.update(request);
    BaseResponse<Producto> response = new BaseResponse<>();

    return response.buildResponseEntity(HttpStatus.OK, "CLiente actualizada exitosamente",
        persona);
  }

  @GetMapping("/findAll")
  @Operation(summary = "Obtener todas las productos")
  public ResponseEntity<Response<List<Producto>>> findAll() {
    List<Producto> personas = service.findAll();
    BaseResponse<List<Producto>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Producto encontradas:"+personas.size(), personas);
  }
  @DeleteMapping()
  @Operation(summary = "Eliminar producto segun id")
  public ResponseEntity<Response<String>> delete(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a eliminar")
                                               @RequestBody @Valid ProductoRequestDTO request) {
    String deleteResult = service.delete(request);
    BaseResponse<String> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, deleteResult, deleteResult);
  }

}
