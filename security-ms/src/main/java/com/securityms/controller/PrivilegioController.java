package com.securityms.controller;

import com.securityms.controller.request.PrivilegioRequestDTO;
import com.securityms.controller.response.BaseResponse;
import com.securityms.controller.response.Response;
import com.securityms.repository.domain.Privilegio;
import com.securityms.service.implementation.PrivilegioServiceImpl;
import com.securityms.util.privilege.Privilege;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privilegio")
public class PrivilegioController {

    @Autowired
    private PrivilegioServiceImpl service;

    @Privilege("MANTENIMIENTO_ROLES")
    @GetMapping("/findAll")
    @Operation(summary = "Obtener la lista de todos los privilegios")
    public ResponseEntity<Response<List<Privilegio>>> findAll() {
        BaseResponse<List<Privilegio>> response = new BaseResponse<>();
        List<Privilegio> privilegioList = service.findAll();
        return response.buildResponseEntity(HttpStatus.OK, "Privilegios encontrados.", privilegioList);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @GetMapping("/findByRol/{idRol}")
    @Operation(summary = "Obtener los privilegio para un ID rol")
    public ResponseEntity<Response<List<Privilegio>>> findByRol(
            @Parameter(description = "ID de rol") @PathVariable("idRol") Integer idRol) {
        BaseResponse<List<Privilegio>> response = new BaseResponse<>();
        List<Privilegio> privilegioList = service.findAllByRol(idRol);
        return response.buildResponseEntity(HttpStatus.OK, "Privilegios encontrados.", privilegioList);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @GetMapping("/findByRol/{idRol}/paged")
    @Operation(summary = "Obtener los privilegio para un ID rol de una manera paginada")
    public ResponseEntity<Response<Page<Privilegio>>> findByRol(
            @Parameter(description = "ID de rol") @PathVariable("idRol") Integer idRol,
            @Parameter(description = "Número de página") @RequestParam Integer page,
            @Parameter(description = "Tamaño de página") @RequestParam Integer size) {

        PageRequest pageable = PageRequest.of(page, size);
        BaseResponse<Page<Privilegio>> response = new BaseResponse<>();
        Page<Privilegio> privilegioList = service.findAllByRol(idRol, pageable);
        return response.buildResponseEntity(HttpStatus.OK, "Privilegios encontrados.", privilegioList);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @PutMapping("/addPrivileges")
    @Operation(summary = "Agregar privilegios")
    public ResponseEntity<Response<Void>> addPrivileges(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de los privilegios a agregar")
            @RequestBody @Valid PrivilegioRequestDTO request) {
        BaseResponse<Void> response = new BaseResponse<>();
        service.addPrivileges(request);
        return response.buildResponseEntity(HttpStatus.OK, "Privilegios agregados.", null);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @PutMapping("/removePrivileges")
    @Operation(summary = "Eliminar privilegios")
    public ResponseEntity<Response<Void>> removePrivileges(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de los privilegios a eliminar")
            @RequestBody @Valid PrivilegioRequestDTO request) {
        BaseResponse<Void> response = new BaseResponse<>();
        service.removePrivileges(request);
        return response.buildResponseEntity(HttpStatus.OK, "Privilegios eliminados.", null);
    }
}
