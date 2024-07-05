package com.cuentamovimiento.service.feignClient;

import com.cuentamovimiento.repository.domain.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@FeignClient(name = "Cliente",url = "http://localhost:8130")
public interface ClientFeight {

    @GetMapping("/cliente?request={id}")
    HashMap<String,Object> obtenerCliente(@PathVariable Integer id);

}
