package com.securityms.service.feignClient.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserCreatedRequestDTO {

    @NonNull
    private String fullName;
    @NonNull
    private String email;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String host;
    private String beca;

    public UserCreatedRequestDTO(){}
}
