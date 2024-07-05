package com.securityms.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class ApplicationIncompleteInfo {

    private String numeroIdentificacion;
    private String idRespuesta;
    private Integer idSolicitud;
    private String token;

}
