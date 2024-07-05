package com.securityms.controller.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class ValidatePasswordUpdateCodeRequestDTO {

    @NonNull
    private String username;
    @NonNull
    private String code;

    public ValidatePasswordUpdateCodeRequestDTO(){}
}
