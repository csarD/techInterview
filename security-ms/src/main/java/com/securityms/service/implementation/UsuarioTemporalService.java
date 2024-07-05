package com.securityms.service.implementation;

import com.securityms.controller.request.LoginRequestDTO;
import com.securityms.controller.request.TemporalUsuarioRequestDTO;
import com.securityms.controller.response.LoginResponseDTO;
import com.securityms.error.exception.ConflictException;
import com.securityms.repository.UsuarioTemporalRepository;
import com.securityms.repository.domain.UsuarioTemporal;
import com.securityms.service.ILoginService;
import com.securityms.service.IUsuarioTemporalService;
import com.securityms.service.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioTemporalService implements IUsuarioTemporalService {

    private final UsuarioTemporalRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ILoginService loginService;
    private final UsuarioService usuarioService;

    @Override
    public LoginResponseDTO saveTemporalUser(TemporalUsuarioRequestDTO usuario) {
        log.info("Create new usuario temporal {}", usuario);

        if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return loginService.loginTemporalUser(new LoginRequestDTO(usuario.getUsuario(), usuario.getUsuario()));
        }

        if (usuarioService.userExists(usuario.getUsuario()))
            throw new ConflictException("Ya existe un usuario para la cédula: " + usuario.getUsuario());

        repository.save(UsuarioTemporal.builder()
                .fechaInsercion(LocalDateTime.now())
                .usuario(usuario.getUsuario())
                .password(passwordEncoder.encode(usuario.getUsuario()))
                .build());

        return loginService.loginTemporalUser(new LoginRequestDTO(usuario.getUsuario(), usuario.getUsuario()));
    }

    @Override
    public void deleteTemporalUserIfExists(String username) {
        repository.findByUsuario(username).ifPresent(repository::delete);
    }

    @Override
    public boolean temporalUserExists(String username) {
        return repository.existsByUsuario(username);
    }

    @Transactional
    @Scheduled(cron = "0 30 2 * * *")
    public void deleteOldTemporalUsers(){
        LocalDateTime fechaInsercion = LocalDateTime.now().minusMonths(4);
        log.info("Delete temporal users before {}", fechaInsercion);
        repository.deleteBeforeFechaInsercion(fechaInsercion);
    }
}
