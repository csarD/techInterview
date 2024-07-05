package com.clientepersona.controller;

import com.clientepersona.controller.request.ClienteRequestDTO;
import com.clientepersona.controller.response.BaseResponse;
import com.clientepersona.controller.response.CustomResponse;
import com.clientepersona.controller.response.Response;
import com.clientepersona.repository.domain.Cliente;
import com.clientepersona.service.implementation.ClienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

  @Autowired
  private ClienteServiceImpl service;
  @GetMapping()
  @Operation(summary = "Obtener cliente segun id")
  public ResponseEntity<Response<Cliente>> PersonInfo(

          @RequestParam  String request) {

    Cliente persona = service.get(request);
    BaseResponse<Cliente> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cliente encontrado ",
            persona);
  }

  @PostMapping()
  @Operation(summary = "Nuevo Cliente")
  public ResponseEntity<Response<Cliente>> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de Cliente a guardar")
      @RequestBody @Valid ClienteRequestDTO request) {


    Cliente persona = service.save(request);
    BaseResponse<Cliente> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cliente guardada exitosamente",
            persona);
  }


  @PutMapping()
  @Operation(summary = "Actualizar Cliente")
  public ResponseEntity<Response<Cliente>> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a guardar")
      @RequestBody @Valid ClienteRequestDTO request) {
    Cliente persona = service.update(request);
    BaseResponse<Cliente> response = new BaseResponse<>();
    CustomResponse<Object> responseEntity = new CustomResponse<>();

    return response.buildResponseEntity(HttpStatus.OK, "CLiente actualizada exitosamente",
        persona);
  }

  @GetMapping("/findAll")
  @Operation(summary = "Obtener todas las Clientes")
  public ResponseEntity<Response<List<Cliente>>> findAll() {
    List<Cliente> personas = service.findAll();
    BaseResponse<List<Cliente>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cliente encontradas:"+personas.size(), personas);
  }
  @DeleteMapping()
  @Operation(summary = "Eliminar cliente segun id")
  public ResponseEntity<Response<String>> delete(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a eliminar")
                                               @RequestBody @Valid ClienteRequestDTO request) {
    String deleteResult = service.delete(request);
    BaseResponse<String> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, deleteResult, deleteResult);
  }

}
