package com.securityms.service;

import com.securityms.controller.request.TemporalUsuarioRequestDTO;
import com.securityms.controller.response.LoginResponseDTO;

public interface IUsuarioTemporalService {

    LoginResponseDTO saveTemporalUser(TemporalUsuarioRequestDTO usuario);

    void deleteTemporalUserIfExists(String username);

    boolean temporalUserExists(String username);
}
