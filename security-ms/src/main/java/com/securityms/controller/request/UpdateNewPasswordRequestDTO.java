package com.securityms.controller.request;

import com.securityms.util.stringNormalized.StringNormalized;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateNewPasswordRequestDTO {

    @StringNormalized
    @NonNull
    private String usuario;
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;

    public UpdateNewPasswordRequestDTO(){}
}
