package com.securityms.repository.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PrivatePromotionInfo {
    @NonNull
    private Integer idPromocion;

}
