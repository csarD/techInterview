package com.clientepersona.controller;

import com.clientepersona.controller.request.PersonaRequestDTO;
import com.clientepersona.controller.response.BaseResponse;
import com.clientepersona.controller.response.CustomResponse;
import com.clientepersona.controller.response.Response;
import com.clientepersona.repository.domain.Persona;
import com.clientepersona.service.implementation.PersonaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {

  @Autowired
  private PersonaServiceImpl service;
  @GetMapping()
  @Operation(summary = "Obtener persona segun id")
  public ResponseEntity<Response<Persona>> PersonInfo(

          @RequestParam  String request) {
    System.out.println(request);

    Persona persona = service.get(request);
    BaseResponse<Persona> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Persona encontrada ",
            persona);
  }

  @PostMapping()
  @Operation(summary = "Nueva Persona")
  public ResponseEntity<Response<Persona>> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a guardar")
      @RequestBody @Valid PersonaRequestDTO request) {
    System.out.println(request);

    Persona persona = service.save(request);
    BaseResponse<Persona> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Persona guardada exitosamente",
            persona);
  }


  @PutMapping()
  @Operation(summary = "Actualizar persona")
  public ResponseEntity<Response<Persona>> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a guardar")
      @RequestBody @Valid PersonaRequestDTO request) {
    Persona persona = service.update(request);
    BaseResponse<Persona> response = new BaseResponse<>();
    CustomResponse<Object> responseEntity = new CustomResponse<>();

    return response.buildResponseEntity(HttpStatus.OK, "Persona actualizada exitosamente",
        persona);
  }

  @GetMapping("/findAll")
  @Operation(summary = "Obtener todas las personas")
  public ResponseEntity<Response<List<Persona>>> findAll() {
    List<Persona> personas = service.findAll();
    BaseResponse<List<Persona>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Personas encontradas:"+personas.size(), personas);
  }
  @DeleteMapping()
  @Operation(summary = "Eliminar persona segun id")
  public ResponseEntity<Response<String>> delete(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de persona a eliminar")
                                               @RequestBody @Valid PersonaRequestDTO request) {
    String deleteResult = service.delete(request);
    BaseResponse<String> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, deleteResult, deleteResult);
  }

}
