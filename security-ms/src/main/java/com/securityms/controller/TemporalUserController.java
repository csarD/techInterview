package com.securityms.controller;

import com.securityms.controller.request.TemporalUsuarioRequestDTO;
import com.securityms.controller.response.LoginResponseDTO;
import com.securityms.service.IUsuarioTemporalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarioTemporal")
@RequiredArgsConstructor
public class TemporalUserController {

    private final IUsuarioTemporalService service;

    @PostMapping
    @Operation(summary = "Crear nuevo usuario temporal")
    public ResponseEntity<LoginResponseDTO> handleTemporalUser(@RequestBody TemporalUsuarioRequestDTO request) {
        LoginResponseDTO response = service.saveTemporalUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
