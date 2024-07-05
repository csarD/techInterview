package com.securityms.service;

import com.securityms.repository.dto.EvaluationTokenInfo;

public interface IEvaluationTokenService {
    String generateTokenForEvaluation(String numeroIdentificacion, Integer idFaseTipoPruebaAcademica);
    EvaluationTokenInfo validateTokenForEvaluation(String token);
}
