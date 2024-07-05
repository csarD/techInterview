package com.cuentamovimiento.controller;

import com.clientepersona.controller.request.ClienteRequestDTO;
import com.clientepersona.service.implementation.ClienteServiceImpl;

import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.controller.response.BaseResponse;
import com.cuentamovimiento.controller.response.CustomResponse;
import com.cuentamovimiento.controller.response.Response;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.service.implementation.CuentaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

  @Autowired
  private CuentaServiceImpl service;
  @GetMapping()
  @Operation(summary = "Obtener cuentas segun clientid")
  public ResponseEntity<Response<List<Integer>>> PersonAccount(

          @RequestParam  Integer request) {

    List<Integer> persona = service.getAllClientAccounts(request);
    BaseResponse<List<Integer>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cuenta encontrada ",
            persona);
  }
  @GetMapping("/")
  @Operation(summary = "Obtener cuenta segun id")
  public ResponseEntity<Response<Cuenta>> PersonInfo(

          @RequestParam  Integer request) {

    Cuenta persona = service.get(request);
    BaseResponse<Cuenta> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cuenta encontrada ",
            persona);
  }

  @PostMapping()
  @Operation(summary = "Nueva Cuenta")
  public ResponseEntity<Response<Cuenta>> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de Cliente a guardar")
      @RequestBody @Valid CuentaRequestDTO request) {


    Cuenta persona = service.save(request);
    BaseResponse<Cuenta> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cliente guardada exitosamente",
            persona);
  }


  @PutMapping()
  @Operation(summary = "Actualizar Estado de Cuenta(Activa o Inactiva)")
  public ResponseEntity<Response<Cuenta>> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de cuenta a actualizar")
      @RequestBody @Valid CuentaRequestDTO request) {
    Cuenta persona = service.update(request);
    BaseResponse<Cuenta> response = new BaseResponse<>();

    return response.buildResponseEntity(HttpStatus.OK, "CLiente actualizada exitosamente",
        persona);
  }

  @GetMapping("/findAll")
  @Operation(summary = "Obtener todas las cuentas")
  public ResponseEntity<Response<List<Cuenta>>> findAll() {
    List<Cuenta> personas = service.findAll();
    BaseResponse<List<Cuenta>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cuentas encontradas:"+personas.size(), personas);
  }
  @DeleteMapping()
  @Operation(summary = "Eliminar Cuenta segun id")
  public ResponseEntity<Response<String>> delete(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a eliminar")
                                               @RequestBody @Valid CuentaRequestDTO request) {
    String deleteResult = service.delete(request);
    BaseResponse<String> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, deleteResult, deleteResult);
  }

}
