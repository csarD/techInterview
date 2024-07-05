package com.securityms.service.feignClient;

import com.securityms.service.feignClient.request.UserCreatedRequestDTO;
import com.securityms.service.feignClient.request.UserPasswordCodeRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notifications")
public interface NotificationEmailProxy {

    @PostMapping("/api/usuario/userCreated")
    ResponseEntity<Void> userCreated(@RequestBody UserCreatedRequestDTO request);
    @PostMapping("/api/usuario/userPasswordCode")
    ResponseEntity<Void> userPasswordCode(@RequestBody UserPasswordCodeRequestDTO request);
}
