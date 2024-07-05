package com.securityms.service;

import com.securityms.controller.request.LoginRequestDTO;
import com.securityms.controller.response.LoginResponseDTO;

import javax.naming.AuthenticationException;

public interface ILoginService {

    LoginResponseDTO login(LoginRequestDTO request);

    LoginResponseDTO loginTemporalUser(LoginRequestDTO request);

    boolean checkPrivilege(String token, String privilegeName) throws AuthenticationException;
}
