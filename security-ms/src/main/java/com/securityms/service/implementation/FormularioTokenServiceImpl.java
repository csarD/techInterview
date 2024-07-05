package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.controller.request.LoginRequestDTO;
import com.securityms.controller.response.PromocionResponseDTO;
import com.securityms.repository.dto.MissingNotificationInfo;
import com.securityms.repository.dto.PrivatePromotionInfo;
import com.securityms.repository.enums.EstadoPromocion;
import com.securityms.service.IFormularioTokenService;
import com.securityms.service.ILoginService;
import com.securityms.service.feignClient.MultibecasServiceProxy;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FormularioTokenServiceImpl implements IFormularioTokenService {

  private final Logger logger = LogManager.getLogger(FormularioTokenServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Value("${jwt.secret}")
  private String jwtSecret;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private MultibecasServiceProxy multibecasServiceProxy;
  @Autowired
  private ILoginService loginService;

  @Override
  public String generateTokenForMissingNotification(String numeroIdentificacion, String idRespuesta,
      Integer idFaseSolicitud) {
    try {
      long nowMillis = System.currentTimeMillis();

      MissingNotificationInfo missingNotificationInfo = new MissingNotificationInfo(
          numeroIdentificacion,
          idRespuesta,
          idFaseSolicitud
      );

      return Jwts.builder()
          .setSubject(numeroIdentificacion)
          .setIssuedAt(new Date(nowMillis))
          .claim("MissingNotificationInfo", missingNotificationInfo)
          .signWith(SignatureAlgorithm.HS512, jwtSecret)
          .compact();
    } catch (Exception e) {
      logger.error("Error al generar el token de notificación faltante: " + e.getMessage());
      throw new RuntimeException(
          "Error al generar el token de notificación faltante: " + e.getMessage());
    }
  }

  @Override
  public MissingNotificationInfo validateTokenForMissingNotification(String token)
      throws Exception {
    try {
      Claims claims = jwtUtil.getClaimsWithoutUserValidation(token);
      MissingNotificationInfo tokenInfo = modelMapper.map(claims.get("MissingNotificationInfo"),
          MissingNotificationInfo.class);

      tokenInfo.setToken(loginService.loginTemporalUser(new LoginRequestDTO(
              tokenInfo.getNumeroIdentificacion(),
              tokenInfo.getNumeroIdentificacion())).getToken());

      if (multibecasServiceProxy.validateIfFaseIsInProgress(tokenInfo.getIdFaseSolicitud())) {
        return tokenInfo;
      } else {
        throw new Exception("La fase no se encuentra activa");
      }
    } catch (Exception e) {
      logger.error("Error al validar el token de notificación faltante: " + e.getMessage());
      throw new Exception("Error al validar el token de notificación faltante: " + e.getMessage());
    }
  }
}
