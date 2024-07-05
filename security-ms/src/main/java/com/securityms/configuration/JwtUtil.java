package com.securityms.configuration;

import com.securityms.repository.dto.UserInfoJwt;
import com.securityms.service.IUsuarioTemporalService;
import com.securityms.service.implementation.LoginServiceImpl;
import io.jsonwebtoken.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private IUsuarioTemporalService usuarioTemporalService;

    public Claims getClaims(String token) throws AuthenticationException {
        Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        if (!loginService.validateUsername(body.getSubject()))
            throw new AuthenticationException("Invalid JWT token");
        return body;
    }

    public String validateTemporalToken(String token) throws AuthenticationException {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            if (!usuarioTemporalService.temporalUserExists(body.getSubject()))
                throw new AuthenticationException("Invalid JWT token");
            return body.getSubject();
        } catch (SignatureException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("JWT claims string is empty.");
        }
    }

    public Claims getClaimsWithoutUserValidation(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserInfoJwt userInfoJwt) {

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + (tokenValidity * 60000);
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setSubject(userInfoJwt.getUsername())
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(exp)
                .claim("userInfoJwt", userInfoJwt)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public void validateToken(String token) throws AuthenticationException {
        try {
            getClaims(token);
        } catch (SignatureException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("JWT claims string is empty.");
        }
    }

    public UserInfoJwt tokenInfo(String token) throws AuthenticationException {

        ModelMapper modelMapper = new ModelMapper();
        Claims claims = getClaims(token);

        return modelMapper.map(claims.get("userInfoJwt"), UserInfoJwt.class);
    }

}
