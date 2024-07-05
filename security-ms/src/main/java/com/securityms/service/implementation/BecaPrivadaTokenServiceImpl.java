package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.repository.dto.BecaPrivadaTokenInfo;
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
public class BecaPrivadaTokenServiceImpl {

  private final Logger logger = LogManager.getLogger(BecaPrivadaTokenServiceImpl.class);

  private final ModelMapper modelMapper = new ModelMapper();
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Autowired
  private JwtUtil jwtUtil;

  public String generateTokenForPrivateBeca(String numeroIdentificacion, String correo,
      Integer idPromocion) {
    try {
      logger.info("Generating token for private beca: {}", numeroIdentificacion);
      long nowMillis = System.currentTimeMillis();

      BecaPrivadaTokenInfo tokenInfo = new BecaPrivadaTokenInfo(
          idPromocion,
          numeroIdentificacion,
          correo
      );

      return Jwts.builder()
          .setSubject(numeroIdentificacion)
          .setIssuedAt(new Date(nowMillis))
          .claim("BecaPrivadaTokenInfo", tokenInfo)
          .signWith(SignatureAlgorithm.HS512, jwtSecret)
          .compact();
    } catch (Exception e) {
      logger.error("Error al generar el token de beca privada: {}", e.getMessage());
      throw new RuntimeException("Error al generar el token de beca privada: " + e.getMessage());
    }
  }

  public BecaPrivadaTokenInfo validateTokenForPrivateBeca(String token) {
    try {
      Claims claims = jwtUtil.getClaimsWithoutUserValidation(token);
      return modelMapper.map(claims.get("BecaPrivadaTokenInfo"), BecaPrivadaTokenInfo.class);
    } catch (Exception e) {
      logger.error("Error al validar el token de beca privada: {}", e.getMessage());
      throw new RuntimeException("Error al validar el token de beca privada: " + e.getMessage());
    }
  }

}
