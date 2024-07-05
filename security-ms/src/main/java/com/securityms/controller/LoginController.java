package com.securityms.controller;

import com.securityms.configuration.JwtUtil;
import com.securityms.controller.request.LoginRequestDTO;
import com.securityms.controller.response.BaseResponse;
import com.securityms.controller.response.LoginResponseDTO;
import com.securityms.controller.response.Response;
import com.securityms.repository.dto.UserInfoJwt;
import com.securityms.service.implementation.LoginServiceImpl;
import com.securityms.util.privilege.Privilege;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/session")
public class LoginController {

    @Autowired
    private LoginServiceImpl service;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "Ingresar al sistema")
    public ResponseEntity<Response<LoginResponseDTO>> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciales para ingresar al sistema")
        @RequestBody @Valid LoginRequestDTO request){
        LoginResponseDTO credentials = service.login(request);
        BaseResponse<LoginResponseDTO> response = new BaseResponse<>();

        if(credentials == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(!credentials.getPasswordUpdated())
            return response.buildResponseEntity(HttpStatus.FORBIDDEN, "Debe actualizar la contraseña", null);

        return response.buildResponseEntity(HttpStatus.OK, "Logueado con exito", credentials);
    }

    @GetMapping("/checkPrivilege")
    @Operation(summary = "Comprobar un privilegio")
    public ResponseEntity<Boolean> checkPrivilege(
        @Parameter(description = "Token a validar") @RequestParam("token") String token,
        @Parameter(description = "Nombre del privilegio") @RequestParam("privilegeName") String privilegeName) {
        try {
            boolean response = service.checkPrivilege(token, privilegeName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage());
        }
    }

    @PostMapping("/validateToken")
    @Operation(summary = "Validar token de autenticación")
    public void validateToken(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Token a validar")
        @RequestBody String token) throws AuthenticationException {
        jwtUtil.validateToken(token);
    }

    @PostMapping("/validateTemporalToken")
    @Operation(summary = "Validar token temporal de autenticación")
    public String validateTemporalToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Token a validar")
            @RequestBody String token) throws AuthenticationException {
        return jwtUtil.validateTemporalToken(token);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @GetMapping("/tokenInfo/{token}")
    @Operation(summary = "Información de un token")
    public UserInfoJwt tokeInfo(
        @Parameter(description = "Token a mostrar") @PathVariable("token") String token) throws AuthenticationException {
        return service.tokeInfo(token);
    }


}
