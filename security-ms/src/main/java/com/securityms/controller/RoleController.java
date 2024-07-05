package com.securityms.controller;

import com.securityms.controller.request.RoleRequestDTO;
import com.securityms.controller.response.BaseResponse;
import com.securityms.controller.response.Response;
import com.securityms.controller.response.RoleResponseDTO;
import com.securityms.service.implementation.RoleServiceImpl;
import com.securityms.util.privilege.Privilege;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleServiceImpl service;

    @Privilege("MANTENIMIENTO_ROLES")
    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID")
    public ResponseEntity<Response<RoleResponseDTO>> findById(
        @Parameter(description = "ID de rol") @PathVariable("id") final Integer id) {
        RoleResponseDTO rol = service.findById(id);
        BaseResponse<RoleResponseDTO> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.OK, "Rol encontrado.", rol);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @GetMapping("/findAll")
    @Operation(summary = "Obtener todos los roles")
    public ResponseEntity<List<RoleResponseDTO>> findAll() {

        List<RoleResponseDTO> roleList = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(roleList);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @PostMapping
    @Operation(summary = "Guardar un rol")
    public ResponseEntity<Response<RoleResponseDTO>> saveRole(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del rol a guardar")
        @RequestBody @Valid RoleRequestDTO request) {
        RoleResponseDTO role = service.saveRole(request);
        BaseResponse<RoleResponseDTO> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.CREATED, "Rol guardado con exito", role);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @DeleteMapping
    @Operation(summary = "Eliminar un rol por ID")
    public ResponseEntity<Response<Void>> deleteById(
        @Parameter(description = "ID de rol") @RequestParam final Integer id) {
        service.delete(id);
        BaseResponse<Void> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.OK, "Rol eliminado con exito", null);
    }

    @Privilege("MANTENIMIENTO_ROLES")
    @PutMapping
    @Operation(summary = "Actualizar un rol")
    public ResponseEntity<Response<RoleResponseDTO>> updateRole(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del rol a actualizar")
        @RequestBody @Valid RoleRequestDTO request) {
        RoleResponseDTO role = service.updateRole(request);
        BaseResponse<RoleResponseDTO> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.CREATED, "Rol actualizo con exito", role);
    }

}
