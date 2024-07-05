package com.securityms.controller.request;

import com.securityms.util.stringNormalized.StringNormalized;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TemporalUsuarioRequestDTO {

    @StringNormalized
    @NotNull(message = "usuario: no debe ser null")
    private String usuario;
}
