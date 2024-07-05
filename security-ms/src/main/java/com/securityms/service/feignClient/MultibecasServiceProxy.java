package com.securityms.service.feignClient;

import com.securityms.controller.response.PromocionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "multibecas")
public interface MultibecasServiceProxy {
    @GetMapping("/api/solicitud/validateIfFaseIsInProgress/{idFaseSolicitud}")
    Boolean validateIfFaseIsInProgress(@PathVariable("idFaseSolicitud") Integer idFaseSolicitud);

    @GetMapping("/api/promocion/{id}")
    Optional<PromocionResponseDTO> findById(@PathVariable("id") Integer id);
}
