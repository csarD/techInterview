package com.securityms.controller.request;

import com.securityms.util.stringNormalized.StringNormalized;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdatePasswordRequestDTO {

    @StringNormalized
    @NonNull
    private String username;
    @NonNull
    private String newPassword;

    public UpdatePasswordRequestDTO(){}
}
