package com.securityms.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private Integer idUsuario;
    private String usuario;
    private String firstName;
    private String lastName;
    private String roleName;
    private Integer idRole;
    private String token;
    private Boolean passwordUpdated;
    private List<String> privilegeList = new ArrayList<>();

    public LoginResponseDTO(){}
}

