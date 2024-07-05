package com.securityms.controller.request;

import com.securityms.util.stringNormalized.StringNormalized;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LoginRequestDTO {

    @StringNormalized
    @NonNull
    private String usuario;

    @NonNull
    private String password;

    public LoginRequestDTO(){}
}
