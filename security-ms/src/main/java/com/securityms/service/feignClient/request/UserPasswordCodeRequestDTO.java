package com.securityms.service.feignClient.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class UserPasswordCodeRequestDTO {

    @NonNull
    private String fullName;

    @NonNull
    private String email;

    @NonNull
    private String code;

    public UserPasswordCodeRequestDTO(){}
}
