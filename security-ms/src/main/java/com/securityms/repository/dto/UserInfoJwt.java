package com.securityms.repository.dto;

import lombok.Data;

@Data
public class UserInfoJwt {
    private Integer userId;
    private String username;
    private String roleName;
    private Integer roleId;
    private String email;
    private String firstName;
    private String lastName;

    public UserInfoJwt(){}
}
