package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.repository.dto.InterviewTokenInfo;
import com.securityms.repository.enums.InterviewEmailAction;
import com.securityms.service.IInterviewTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InterviewTokenServiceImpl implements IInterviewTokenService {

  private final Logger logger = LogManager.getLogger(FormularioTokenServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public String generateTokenForInterview(String numeroIdentificacion,
      Integer idFaseTipoEntrevista,
      InterviewEmailAction action) {
    try {
      long nowMillis = System.currentTimeMillis();

      InterviewTokenInfo tokenInfo = new InterviewTokenInfo(
          numeroIdentificacion,
          idFaseTipoEntrevista,
          action
      );

      return Jwts.builder()
          .setSubject(numeroIdentificacion)
          .setIssuedAt(new Date(nowMillis))
          .claim("InterviewTokenInfo", tokenInfo)
          .signWith(SignatureAlgorithm.HS512, jwtSecret)
          .compact();
    } catch (Exception e) {
      logger.error("Error al generar el token de entrevista: " + e.getMessage());
      throw new RuntimeException("Error al generar el token de entrevista: " + e.getMessage());
    }
  }


  @Override
  public InterviewTokenInfo validateTokenForInterview(String token) {
    try {
      Claims claims = jwtUtil.getClaimsWithoutUserValidation(token);
      return modelMapper.map(claims.get("InterviewTokenInfo"), InterviewTokenInfo.class);
    } catch (Exception e) {
      logger.error("Error al validar el token de entrevista: " + e.getMessage());
      throw new RuntimeException("Error al validar el token de entrevista: " + e.getMessage());
    }
  }

}
