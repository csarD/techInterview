package com.securityms.service;

import com.securityms.repository.dto.MissingNotificationInfo;
import com.securityms.repository.dto.PrivatePromotionInfo;

public interface IFormularioTokenService {
    String generateTokenForMissingNotification(String numeroIdentificacion, String idRespuesta, Integer idFaseSolicitud);
    MissingNotificationInfo validateTokenForMissingNotification(String token) throws Exception;
}
