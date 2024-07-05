package com.securityms.service;

import com.securityms.repository.dto.InterviewTokenInfo;
import com.securityms.repository.enums.InterviewEmailAction;

public interface IInterviewTokenService {
    String generateTokenForInterview(String numeroIdentificacion, Integer idFaseTipoEntrevista, InterviewEmailAction action);
    InterviewTokenInfo validateTokenForInterview(String token);
}
