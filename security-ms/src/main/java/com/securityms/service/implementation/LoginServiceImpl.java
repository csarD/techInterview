package com.securityms.service.implementation;

import com.securityms.configuration.JwtUtil;
import com.securityms.controller.request.LoginRequestDTO;
import com.securityms.controller.response.LoginResponseDTO;
import com.securityms.controller.response.RoleResponseDTO;
import com.securityms.repository.UsuarioRepository;
import com.securityms.repository.UsuarioTemporalRepository;
import com.securityms.repository.domain.Usuario;
import com.securityms.repository.dto.UserInfoJwt;
import com.securityms.service.ILoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    private final RoleServiceImpl roleService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioTemporalRepository usuarioTemporalRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    private final PrivilegioServiceImpl privilegioService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            Optional<Usuario> user = validateUser(request);

            if (user.isEmpty()) {
                logger.error("This user doesn't exist: {}", request);
                return null;
            } else if (!user.get().getEstado()) {
                logger.error("This user is disabled: {}", request);
                return null;
            }

            LoginResponseDTO loginResponse = new LoginResponseDTO();
            loginResponse.setPasswordUpdated(user.get().getPasswordUpdated());

            if (!loginResponse.getPasswordUpdated()) {
                return loginResponse;
            }

            loginResponse.setIdUsuario(user.get().getId());
            loginResponse.setFirstName(user.get().getPrimerNombre());
            loginResponse.setLastName(user.get().getPrimerApellido());

            UserInfoJwt userInfoJwt = createUserInfoJwt(user.get());
            String token = jwtUtil.generateToken(userInfoJwt);
            loginResponse.setToken(token);

            Integer idRole = user.get().getIdRole();
            if (idRole != null) {
                RoleResponseDTO role = roleService.findById(idRole);
                loginResponse.setRoleName(role.getNombreRol());
                loginResponse.setIdRole(role.getId());

                List<String> privilegeList = privilegioService.getPrivilegesStringList(idRole);
                loginResponse.setPrivilegeList(privilegeList);
            }

            return loginResponse;
        } catch (Exception e) {
            logger.error("An error occurred during login: {}", e.getMessage());
            throw new RuntimeException("An error occurred during login: " + e.getMessage());
        }
    }

    @Override
    public LoginResponseDTO loginTemporalUser(LoginRequestDTO request) {
        LoginResponseDTO loginResponse = new LoginResponseDTO();

        loginResponse.setUsuario(request.getUsuario());

        UserInfoJwt userInfoJwt = new UserInfoJwt();
        userInfoJwt.setUsername(request.getUsuario());

        String token = jwtUtil.generateToken(userInfoJwt);
        loginResponse.setToken(token);

        return loginResponse;
    }

    @Override
    public boolean checkPrivilege(String token, String privilegeName) throws AuthenticationException {
        try {

            // In case of temporal users
            if (privilegeName.equalsIgnoreCase("PRE_BECARIO")) {
                Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
                return usuarioTemporalRepository.existsByUsuario(body.getSubject());
            }

            jwtUtil.validateToken(token);
            UserInfoJwt userInfoJwt = jwtUtil.tokenInfo(token);

            List<String> privilegeList = privilegioService.getPrivilegesStringList(
                    userInfoJwt.getRoleId());
            return privilegeList.contains(privilegeName);
        } catch (JwtException e) {
            throw new AuthenticationException("Invalid token");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while checking privilege", e);
        }
    }


    public UserInfoJwt tokeInfo(String token) throws AuthenticationException {
        jwtUtil.validateToken(token);
        return jwtUtil.tokenInfo(token);
    }

    public Optional<Usuario> validateUser(LoginRequestDTO request) {
        try {
            Optional<Usuario> user = usuarioRepository.findByUsuario(request.getUsuario());
            if (user.isEmpty()) {
                logger.warn("User doesn't exist for this usuario: {}", request.getUsuario());
                return Optional.empty();
            }

            boolean match = passwordEncoder.matches(request.getPassword(), user.get().getPassword());
            if (match) {
                return user;
            }
            logger.error("Password incorrect for this user: {}", request);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("An error occurred while validating user: {}", e.getMessage());
            return Optional.empty();
        }
    }


    public boolean validateUsername(String username) {
        try {
            Optional<Usuario> user = usuarioRepository.findByUsuario(username);
            if (user.isEmpty()) {
                logger.error("This user doesn't exist: {}", username);
            }
            return user.isPresent();
        } catch (Exception e) {
            logger.error("Error while validating username: {}", username, e);
            throw new RuntimeException("Error while validating username: " + username, e);
        }
    }


    public UserInfoJwt createUserInfoJwt(Usuario usuario) {
        try {
            UserInfoJwt subJwt = new UserInfoJwt();
            subJwt.setUserId(usuario.getId());
            subJwt.setUsername(usuario.getUsuario());
            subJwt.setEmail(usuario.getEmail());
            subJwt.setFirstName(usuario.getPrimerNombre());
            subJwt.setLastName(usuario.getPrimerApellido());

            if (usuario.getIdRole() != null) {
                RoleResponseDTO role = roleService.findById(usuario.getIdRole());
                subJwt.setRoleName(role.getNombreRol());
                subJwt.setRoleId(role.getId());
            }

            return subJwt;
        } catch (Exception e) {
            logger.error("Error creating UserInfoJwt: {}", e.getMessage());
            throw new RuntimeException("Error creating UserInfoJwt");
        }
    }


}
