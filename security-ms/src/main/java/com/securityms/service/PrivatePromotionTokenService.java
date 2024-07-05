package com.securityms.service;

import com.securityms.repository.dto.PrivatePromotionInfo;

public interface PrivatePromotionTokenService {

    PrivatePromotionInfo validateTokenForPrivatePromotion(String token) throws Exception;

    String generateTokenForPrivatePromotion(Integer idPromocion);
}
