package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.controller.request.LoginRequestDTO;
import com.securityms.repository.dto.ApplicationIncompleteInfo;
import com.securityms.service.IApplicationTokenService;
import com.securityms.service.ILoginService;
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

@Service
public class ApplicationTokenServiceImpl implements IApplicationTokenService {

    private final Logger logger = LogManager.getLogger(ApplicationTokenServiceImpl.class);

    private final ModelMapper modelMapper = new ModelMapper();
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ILoginService loginService;

    @Override
    public String generateTokenForIncompleteApplication(String numeroIdentificacion,
                                                        String idRespuesta, Integer idSolicitud) {
        try {
            long nowMillis = System.currentTimeMillis();

            ApplicationIncompleteInfo applicationIncompleteInfo = new ApplicationIncompleteInfo(
                    numeroIdentificacion,
                    idRespuesta,
                    idSolicitud,
                    ""
            );

            return Jwts.builder()
                    .setSubject(numeroIdentificacion)
                    .setIssuedAt(new Date(nowMillis))
                    .claim("ApplicationIncompleteInfo", applicationIncompleteInfo)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } catch (Exception e) {
            logger.error("Error al generar el token: {}", e.getMessage());
            throw new RuntimeException("Error al generar el token: " + e.getMessage());
        }
    }


    @Override
    public ApplicationIncompleteInfo validateTokenForIncompleteApplication(String token) {
        try {
            Claims claims = jwtUtil.getClaimsWithoutUserValidation(token);
            ApplicationIncompleteInfo result = modelMapper.map(
                    claims.get("ApplicationIncompleteInfo"),
                    ApplicationIncompleteInfo.class);

            result.setToken(loginService.loginTemporalUser(new LoginRequestDTO(
                    result.getNumeroIdentificacion(),
                    result.getNumeroIdentificacion())).getToken());
            return result;
        } catch (Exception e) {
            logger.error("Error al validar el token: {}", e.getMessage());
            throw new RuntimeException("Error al validar el token: " + e.getMessage());
        }
    }

}
