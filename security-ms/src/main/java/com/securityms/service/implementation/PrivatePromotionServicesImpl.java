package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.controller.response.PromocionResponseDTO;
import com.securityms.repository.dto.PrivatePromotionInfo;
import com.securityms.repository.enums.EstadoPromocion;
import com.securityms.service.PrivatePromotionTokenService;
import com.securityms.service.feignClient.MultibecasServiceProxy;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PrivatePromotionServicesImpl implements PrivatePromotionTokenService {

    private final Logger logger = LogManager.getLogger(PrivatePromotionServicesImpl.class);

    private final ModelMapper modelMapper = new ModelMapper();
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MultibecasServiceProxy multibecasServiceProxy;


    @Override
    public String generateTokenForPrivatePromotion(Integer idPromocion) {
        try {
            long nowMillis = System.currentTimeMillis();

            PrivatePromotionInfo privatePromotionInfo = new PrivatePromotionInfo(
                    idPromocion
            );

            return Jwts.builder()
                    .setSubject(idPromocion.toString())
                    .setIssuedAt(new Date(nowMillis))
                    .claim("privatePromotionInfo", privatePromotionInfo)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } catch (Exception e) {
            logger.error("Error al generar el token de promocion: " + e.getMessage());
            throw new RuntimeException(
                    "Error al generar el token de promocion : " + e.getMessage());
        }

    }


    @Override
    public PrivatePromotionInfo validateTokenForPrivatePromotion(String token)
            throws Exception {
        try {
            Claims claims = jwtUtil.getClaimsWithoutUserValidation(token);
            PrivatePromotionInfo tokenInfo = modelMapper.map(claims.get("privatePromotionInfo"),
                    PrivatePromotionInfo.class);

            Optional<PromocionResponseDTO> promocion = multibecasServiceProxy.findById(tokenInfo.getIdPromocion());

            if (promocion.get().getEstado().equals(EstadoPromocion.CANCELED) || promocion.get().getEstado().equals(EstadoPromocion.EXPIRED))
                throw new Exception("La promocion no esta en status activa p pendiente");


            if (promocion.get().getPublica())
                throw new Exception("La promocion no es privada");

            return tokenInfo;

        } catch (Exception e) {
            logger.error("Error al validar el token de promocion : " + e.getMessage());
            throw new Exception("Error al validar el token de promocion: " + e.getMessage());
        }
    }

}
