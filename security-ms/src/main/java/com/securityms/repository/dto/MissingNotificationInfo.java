package com.securityms.repository.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MissingNotificationInfo {

    @NonNull
    private String numeroIdentificacion;

    @NonNull
    private String idRespuesta;

    @NonNull
    private Integer idFaseSolicitud;

    private String token;

    public MissingNotificationInfo(){}

}
