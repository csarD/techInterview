package com.securityms.service;

import com.securityms.controller.request.UpdateNewPasswordRequestDTO;
import com.securityms.controller.request.UpdatePasswordRequestDTO;
import com.securityms.controller.request.UsuarioRequestDTO;
import com.securityms.controller.request.ValidatePasswordUpdateCodeRequestDTO;
import com.securityms.controller.response.UsuarioResponseDTO;
import com.securityms.repository.domain.Usuario;
import com.securityms.repository.dto.UsuarioRolRelacionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO findById(Integer userId);

    UsuarioResponseDTO findByIdentification(String identification);

    List<UsuarioResponseDTO> findAll();

    Page<UsuarioResponseDTO> findAll(Pageable pageable);

    UsuarioResponseDTO saveUser(UsuarioRequestDTO usuario);

    void deleteUserById(Integer userId);

    UsuarioResponseDTO updateUser(UsuarioRequestDTO usuario);

    Usuario findByUsuario(String usuario);

    List<UsuarioResponseDTO> findByRole(Integer idRole);

    Page<UsuarioResponseDTO> findByRole(Integer idRole, Pageable pageable);

    void changeRol(UsuarioRolRelacionDTO request);

    void updateNewPassword(UpdateNewPasswordRequestDTO request) throws Exception;

    void disableUser(String username);

    void generatePasswordUpdateCode(String username);

    void validatePasswordUpdateCode(ValidatePasswordUpdateCodeRequestDTO request) throws Exception;

    void updatePassword(UpdatePasswordRequestDTO request) throws Exception;

    boolean userExists(String usuario);
}
