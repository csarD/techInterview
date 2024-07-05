package com.securityms.service;

import com.securityms.repository.dto.ApplicationIncompleteInfo;

public interface IApplicationTokenService {
    String generateTokenForIncompleteApplication(String numeroIdentificacion, String idRespuesta, Integer idSolicitud);
    ApplicationIncompleteInfo validateTokenForIncompleteApplication(String token);
}
