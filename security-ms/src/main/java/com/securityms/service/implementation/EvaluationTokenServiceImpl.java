package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.repository.dto.EvaluationTokenInfo;
import com.securityms.service.IEvaluationTokenService;
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
public class EvaluationTokenServiceImpl implements IEvaluationTokenService {

  private final Logger logger = LogManager.getLogger(EvaluationTokenServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Value("${jwt.secret}")
  private String jwtSecret;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String generateTokenForEvaluation(String numeroIdentificacion, Integer idFaseTipoPruebaAcademica) {
        try {
            long nowMillis = System.currentTimeMillis();

            EvaluationTokenInfo tokenInfo = new EvaluationTokenInfo(
                    numeroIdentificacion,
                    idFaseTipoPruebaAcademica
            );

      return Jwts.builder()
          .setSubject(numeroIdentificacion)
          .setIssuedAt(new Date(nowMillis))
          .claim("EvaluationTokenInfo", tokenInfo)
          .signWith(SignatureAlgorithm.HS512, jwtSecret)
          .compact();
    } catch (Exception e) {
      logger.error("Error al generar el token de evaluación: " + e.getMessage());
      throw new RuntimeException("Error al generar el token de evaluación: " + e.getMessage());
    }
  }


  @Override
  public EvaluationTokenInfo validateTokenForEvaluation(String token) {
    try {
      Claims claims = jwtUtil.getClaimsWithoutUserValidation(token);
      return modelMapper.map(claims.get("EvaluationTokenInfo"), EvaluationTokenInfo.class);
    } catch (Exception e) {
      logger.error("Error al validar el token de evaluación: " + e.getMessage());
      throw new RuntimeException("Error al validar el token de evaluación: " + e.getMessage());
    }
  }

}
