package com.securityms.controller.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PrivilegioRequestDTO {

    @NonNull
    private Integer idRol;

    @NonNull
    private List<Integer> idPrivilegioList = new ArrayList<>();
}
