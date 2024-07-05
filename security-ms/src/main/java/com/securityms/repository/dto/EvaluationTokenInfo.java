package com.securityms.repository.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EvaluationTokenInfo {

    @NonNull
    private String numeroIdentificacion;

    @NonNull
    private Integer idFaseTipoPruebaAcademica;

    public EvaluationTokenInfo(){}
}
