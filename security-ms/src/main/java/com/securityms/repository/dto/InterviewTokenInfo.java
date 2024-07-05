package com.securityms.repository.dto;

import com.securityms.repository.enums.InterviewEmailAction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InterviewTokenInfo {

  @NonNull
  private String numeroIdentificacion;

  @NonNull
  private Integer idFaseTipoEntrevista;

  @NonNull
  @Enumerated(EnumType.STRING)
  private InterviewEmailAction action;

  public InterviewTokenInfo() {
  }
}
