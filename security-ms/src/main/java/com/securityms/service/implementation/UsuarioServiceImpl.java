package com.securityms.service.implementation;

import com.securityms.controller.request.UpdateNewPasswordRequestDTO;
import com.securityms.controller.request.UpdatePasswordRequestDTO;
import com.securityms.controller.request.UsuarioRequestDTO;
import com.securityms.controller.request.ValidatePasswordUpdateCodeRequestDTO;
import com.securityms.controller.response.RoleResponseDTO;
import com.securityms.controller.response.UsuarioResponseDTO;
import com.securityms.repository.RoleRepository;
import com.securityms.repository.TipoIdentificacionRepository;
import com.securityms.repository.UsuarioRepository;
import com.securityms.repository.domain.Role;
import com.securityms.repository.domain.TipoDoc;
import com.securityms.repository.domain.TipoIdentifiacion;
import com.securityms.repository.domain.Usuario;
import com.securityms.repository.dto.UsuarioRolRelacionDTO;
import com.securityms.service.UsuarioService;
import com.securityms.service.feignClient.NotificationEmailProxy;
import com.securityms.service.feignClient.request.UserCreatedRequestDTO;
import com.securityms.service.feignClient.request.UserPasswordCodeRequestDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);
    private final ModelMapper modelMapper = new ModelMapper();

    private static final String USER_NOT_FOUND = "User not found.";

    @Value("${email.website}")
    private String hostWebsite;

    private final UsuarioRepository repository;
    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TipoDocServiceImpl tipoDocService;
    private final RoleServiceImpl roleService;
    private final RoleRepository roleRepository;
    private final NotificationEmailProxy notificationEmailProxy;

    @Override
    public UsuarioResponseDTO findById(final Integer userId) {
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Integer roleId = usuario.getIdRole();
        Optional<Role> rol = null;

        if (roleId != null) {

            rol = roleRepository.findById(roleId);

            usuario.setNombreRol(rol.get().getNombreRol());
        } else {
            usuario.setNombreRol("");
        }


        return mapResponse(usuario);
    }


    @Override
    public UsuarioResponseDTO findByIdentification(String identification) {
        logger.info("Get user by identification: {}", identification);
        Optional<TipoIdentifiacion> tipoIdentifiacion = tipoIdentificacionRepository.findByNumeroIdentifiacion(identification);
        if (tipoIdentifiacion.isPresent()) {
            Usuario usuario = findByUsuario(tipoIdentifiacion.get().getIdUsuario().getUsuario());
            List<TipoIdentifiacion> list = new ArrayList<>();
            list.add(tipoIdentifiacion.get());
            usuario.setTiposIdentificacion(list);
            return mapResponse(usuario);
        } else {
            logger.error("Can't find any tipoIdentification by this numeroIdentification: {}", identification);
            return null;
        }
    }

    public List<UsuarioResponseDTO> findAll() {
        try {
            List<Usuario> usuarios = repository.findAll();
            List<UsuarioResponseDTO> responseDTOs = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                Integer roleId = usuario.getIdRole();
                Optional<Role> rol = null;
                if (roleId != null) {

                    rol = roleRepository.findById(roleId);
                    usuario.setNombreRol(rol.get().getNombreRol());
                } else {
                    usuario.setNombreRol("");
                }

                responseDTOs.add(mapResponse(usuario));
            }

            return responseDTOs;
        } catch (Exception e) {
            logger.error("An error occurred while fetching all users.", e);
            throw new RuntimeException("An error occurred while fetching all users.", e);
        }
    }

    public Page<UsuarioResponseDTO> findAll(Pageable pageable) {
        try {
            Page<Usuario> usuarioList = repository.findAll(pageable);
            return new PageImpl<>(usuarioList.stream()
                    .map(this::mapResponse)
                    .toList(),
                    pageable,
                    usuarioList.getTotalElements());
        } catch (Exception e) {
            logger.error("An error occurred while fetching all users.", e);
            throw new RuntimeException("An error occurred while fetching all users.", e);
        }
    }

    @Override
    public UsuarioResponseDTO saveUser(UsuarioRequestDTO request) {
        logger.info("Save new user: " + request.getUsuario());
        String newPassword = RandomStringUtils.random(15, true, true);
        String pwdEncript = passwordEncoder.encode(newPassword);

        Usuario usuario = modelMapper.map(request, Usuario.class);
        usuario.setPassword(pwdEncript);
        usuario.setFechaInserccion(Instant.now());
        TipoDoc tipoDoc = tipoDocService.findById(request.getIdTipoDoc());

        TipoIdentifiacion tipoIdentifiacion = new TipoIdentifiacion();
        tipoIdentifiacion.setNumeroIdentifiacion(request.getNumeroIdentifiacion());
        tipoIdentifiacion.setIdtipoDoc(tipoDoc);
        tipoIdentifiacion.setIdUsuario(usuario);

        usuario.getTiposIdentificacion().add(tipoIdentifiacion);
        if (tipoIdentificacionRepository.findByNumeroIdentifiacion(tipoIdentifiacion.getNumeroIdentifiacion()).isPresent()) {
            throw new EntityExistsException("Identificacion duplicada: " + tipoIdentifiacion.getNumeroIdentifiacion());
        }

        if (repository.findByUsuario(request.getUsuario()).isPresent())
            throw new EntityExistsException("Usuario duplicado: " + request.getUsuario());

        usuario = repository.save(usuario);

        try {
            UserCreatedRequestDTO userRequest = new UserCreatedRequestDTO(
                    request.getPrimerNombre() + " " + request.getPrimerApellido(),
                    request.getEmail(),
                    request.getUsuario(),
                    newPassword,
                    hostWebsite

            );
            userRequest.setBeca(request.getBeca());
            notificationEmailProxy.userCreated(userRequest);
        } catch (Exception e) {
            logger.error("Can't send email for user created", e);
        }

        if (request.isBecario())
            changeRol(new UsuarioRolRelacionDTO(usuario.getId(), roleService.getBecarioRole().getId()));

        if (request.isFacilitador())
            changeRol(new UsuarioRolRelacionDTO(usuario.getId(), roleService.getFacilitadorRole().getId()));

        return mapResponse(usuario);
    }

    @Override
    public void deleteUserById(Integer userId) {
        try {
            logger.warn("Delete user by id: {}", userId);
            repository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User with ID " + userId + " not found");

        }
    }


    @Override
    public UsuarioResponseDTO updateUser(UsuarioRequestDTO request) {
        try {
            logger.info("Update user: {}", request);
            Usuario oldUsuario = repository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
            Usuario newUsuario = modelMapper.map(request, Usuario.class);
            newUsuario.setId(oldUsuario.getId());
            newUsuario.setTiposIdentificacion(oldUsuario.getTiposIdentificacion());
            newUsuario.setUsuarioInserccion(oldUsuario.getUsuarioInserccion());
            newUsuario.setFechaInserccion(oldUsuario.getFechaInserccion());
            newUsuario.setPassword(oldUsuario.getPassword());
            newUsuario.setIdRole(oldUsuario.getIdRole());
            newUsuario.setPasswordUpdated(oldUsuario.getPasswordUpdated());
            return mapResponse(repository.save(newUsuario));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage());
            throw new RuntimeException("Error updating user");
        }
    }


    @Override
    public Usuario findByUsuario(String usuario) {
        logger.info("Get user by usuario: {}", usuario);
        return repository.findByUsuario(usuario)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public List<UsuarioResponseDTO> findByRole(Integer idRole) {
        logger.info("Get user by role: {}", idRole);
        List<UsuarioResponseDTO> response = new ArrayList<>();
        try {
            RoleResponseDTO role = roleService.findById(idRole);
            List<Usuario> usuarios = repository.findAllByIdRole(idRole);

            for (Usuario usuario : usuarios) {
                usuario.setNombreRol(role.getNombreRol());
                response.add(mapResponse(usuario));
            }
        } catch (EntityNotFoundException ex) {
            logger.error("Error finding role with ID {}: {}", idRole, ex.getMessage());
        }
        return response;
    }

    @Override
    public Page<UsuarioResponseDTO> findByRole(Integer idRole, Pageable pageable) {
        logger.info("Get user by role: {}", idRole);
        List<UsuarioResponseDTO> response = new ArrayList<>();
        try {
            roleService.findById(idRole);
            for (Usuario usuario : repository.findAllByIdRole(idRole, pageable)) {
                response.add(findById(usuario.getId()));
            }
        } catch (EntityNotFoundException ex) {
            logger.error("Error finding role with ID {}: {}", idRole, ex.getMessage());
        }
        return new PageImpl<>(response);
    }


    public UsuarioResponseDTO mapResponse(Usuario usuario) {
        UsuarioResponseDTO response = modelMapper.map(usuario, UsuarioResponseDTO.class);
        response.setNumeroIdentifiacion(usuario.getTiposIdentificacion().get(0).getNumeroIdentifiacion());
        response.setTipoDoc(usuario.getTiposIdentificacion().get(0).getIdtipoDoc());
        return response;
    }

    @Override
    public void changeRol(UsuarioRolRelacionDTO request) {
        logger.info("Change rol for this user: {}", request);
        Usuario usuario = repository.findById(request.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        usuario.setIdRole(request.getIdRol());
        repository.save(usuario);
    }

    public void updateNewPassword(UpdateNewPasswordRequestDTO request) throws Exception {
        logger.info("Update generated password: {}", request);
        Usuario usuario;
        try {
            usuario = findByUsuario(request.getUsuario());
        } catch (EntityNotFoundException ex) {
            throw new Exception("User not found");
        }

        if (request.getOldPassword().equalsIgnoreCase(request.getNewPassword()))
            throw new Exception("Can't use the same password");

        try {
            if (!passwordEncoder.matches(request.getOldPassword(), usuario.getPassword()))
                throw new Exception("Wrong old password");
        } catch (IllegalArgumentException ex) {
            throw new Exception("Invalid password format");
        }

        usuario.setPasswordUpdated(true);
        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(usuario);

        logger.info("Old password updated for this user: {}", request.getUsuario());
    }


    @Override
    public void disableUser(String username) {
        logger.info("Disable this user: {}", username);
        try {
            Usuario usuario = findByUsuario(username);
            usuario.setEstado(false);
            repository.save(usuario);
        } catch (EntityNotFoundException e) {
            logger.error("Error disabling user: {}", e.getMessage());
        }
    }


    @Override
    public void generatePasswordUpdateCode(String username) {
        try {
            logger.info("Generate PasswordUpdateCode for this user: {}", username);
            Usuario usuario = findByUsuario(username);
            String newCode = RandomStringUtils.random(6, true, true);

            usuario.setPasswordUpdateCode(newCode.toUpperCase());
            usuario.setPasswordUpdateCodeValidated(false);
            usuario.setPasswordUpdateCodeTime(LocalDateTime.now());
            repository.save(usuario);

            notificationEmailProxy.userPasswordCode(new UserPasswordCodeRequestDTO(
                    usuario.getPrimerNombre() + " " + usuario.getPrimerApellido(),
                    usuario.getEmail(),
                    newCode.toUpperCase()
            ));

            logger.info("Code to update password (" + newCode.toUpperCase() + ") sent to this email: " + usuario.getEmail());
        } catch (Exception e) {
            logger.error("Error while generating password update code for user {}: {}", username, e.getMessage());
            throw new RuntimeException("Error while generating password update code for user " + username + ": " + e.getMessage());
        }
    }


    @Override
    public void validatePasswordUpdateCode(ValidatePasswordUpdateCodeRequestDTO request) throws Exception {
        logger.info("Validate PasswordUpdateCode: {}", request);
        Usuario usuario = findByUsuario(request.getUsername());

        if (Duration.between(usuario.getPasswordUpdateCodeTime(), LocalDateTime.now()).toMinutes() > 20) {
            usuario.setPasswordUpdateCode(null);
            usuario.setPasswordUpdateCodeTime(null);
            repository.save(usuario);
            throw new Exception("Time expired to use this code");
        }

        if (!usuario.getPasswordUpdateCode().equals(request.getCode()))
            throw new Exception("The code is not the same");

        usuario.setPasswordUpdateCodeValidated(true);
        repository.save(usuario);
    }

    @Override
    public void updatePassword(UpdatePasswordRequestDTO request) throws Exception {
        logger.info("Update password: {}", request);
        Usuario usuario = findByUsuario(request.getUsername());

        if (usuario.getPasswordUpdateCode() == null || !usuario.getPasswordUpdateCodeValidated())
            throw new Exception("Can't update password for this user: " + request.getUsername());

        if (Duration.between(usuario.getPasswordUpdateCodeTime(), LocalDateTime.now()).toMinutes() > 20) {
            usuario.setPasswordUpdateCode(null);
            usuario.setPasswordUpdateCodeTime(null);
            usuario.setPasswordUpdateCodeValidated(false);
            repository.save(usuario);
            throw new Exception("Time expired to update password for this user");
        }

        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuario.setPasswordUpdateCode(null);
        usuario.setPasswordUpdateCodeValidated(false);
        usuario.setPasswordUpdateCodeTime(null);

        repository.save(usuario);
    }

    @Override
    public boolean userExists(String usuario) {
        return repository.existsByUsuario(usuario);
    }
}
