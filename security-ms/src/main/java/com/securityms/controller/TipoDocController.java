package com.securityms.controller;

import com.securityms.repository.domain.TipoDoc;
import com.securityms.service.implementation.TipoDocServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipoDoc")
public class TipoDocController {

    @Autowired
    private TipoDocServiceImpl service;

    @GetMapping("/findAll")
    @Operation(summary = "Obtener todos los tipos de documento")
    public ResponseEntity<List<TipoDoc>> findAll(){
        List<TipoDoc> list = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
