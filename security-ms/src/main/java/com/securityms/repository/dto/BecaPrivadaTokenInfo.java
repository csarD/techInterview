package com.securityms.repository.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor @NoArgsConstructor
public class BecaPrivadaTokenInfo {
    @NonNull
    private Integer idPromocion;
    @NonNull
    private String numeroIdentificacion;
    @NonNull
    private String correo;
}
