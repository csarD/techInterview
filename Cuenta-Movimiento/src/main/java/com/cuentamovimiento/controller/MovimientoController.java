package com.cuentamovimiento.controller;

import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.controller.request.MovimientoRequestDTO;
import com.cuentamovimiento.controller.response.BaseResponse;
import com.cuentamovimiento.controller.response.Response;
import com.cuentamovimiento.repository.domain.Movimiento;
import com.cuentamovimiento.service.implementation.MovimientoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimiento")
public class MovimientoController {

  @Autowired
  private MovimientoServiceImpl service;

  @GetMapping("/")
  @Operation(summary = "Obtener movimiento segun id")
  public ResponseEntity<Response<Movimiento>> PersonInfo(

          @RequestParam  Integer request) {

    Movimiento persona = service.get(request);
    BaseResponse<Movimiento> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Cuenta encontrada ",
            persona);
  }

  @PostMapping()
  @Operation(summary = "Nuevo Movimiento")
  public ResponseEntity<Response<String>> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de Cliente a guardar")
      @RequestBody @Valid MovimientoRequestDTO request) {


    String persona = service.save(request);
    BaseResponse<String> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Procesado de movimiento finalizado",
            persona);
  }


  @PutMapping()
  @Operation(summary = "Actualizar Estado de Cuenta(Activa o Inactiva)")
  public ResponseEntity<Response<String>> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de cuenta a actualizar")
      @RequestBody @Valid CuentaRequestDTO request) {
    //Cuenta persona = service.update(request);
    BaseResponse<String> response = new BaseResponse<>();

    return response.buildResponseEntity(HttpStatus.OK, "Endpoint no disponible",
        "");
  }

  @GetMapping("/findAll")
  @Operation(summary = "Obtener todas las movimientos")
  public ResponseEntity<Response<List<Movimiento>>> findAll() {
    List<Movimiento> personas = service.findAll();
    BaseResponse<List<Movimiento>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Movimientos encontradas:"+personas.size(), personas);
  }
  @DeleteMapping()
  @Operation(summary = "Eliminar movimiento segun id")
  public ResponseEntity<Response<String>> delete(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a eliminar")
                                               @RequestBody @Valid MovimientoRequestDTO request) {
    String deleteResult = service.delete(request);
    BaseResponse<String> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, deleteResult, deleteResult);
  }

}
