package com.securityms.controller;

import com.securityms.controller.response.BaseResponse;
import com.securityms.controller.response.Response;
import com.securityms.repository.dto.*;
import com.securityms.repository.enums.InterviewEmailAction;
import com.securityms.service.implementation.*;
import com.securityms.util.privilege.Privilege;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class InternalTokenController {

  @Autowired
  private FormularioTokenServiceImpl formularioTokenService;

  @Autowired
  private PrivatePromotionServicesImpl privatePromotionServices;
  @Autowired
  private EvaluationTokenServiceImpl evaluationTokenService;
  @Autowired
  private ApplicationTokenServiceImpl applicationTokenService;
  @Autowired
  private InterviewTokenServiceImpl interviewTokenService;
  @Autowired
  private BecaPrivadaTokenServiceImpl becaPrivadaTokenService;

  @Privilege("MANEJAR_SOLICITUD")
  @PostMapping("/generateTokenForMissingNotification")
  @Operation(summary = "Generar token de notificación faltante")
  public String generateTokenForMissingNotification(
      @Parameter(description = "Número de identificación") @RequestParam String numeroIdentificacion,
      @Parameter(description = "ID de respuesta") @RequestParam String idRespuesta,
      @Parameter(description = "ID de Fase solicitud")@RequestParam Integer idFaseSolicitud) {
    return formularioTokenService.generateTokenForMissingNotification(numeroIdentificacion,
        idRespuesta, idFaseSolicitud);
  }

  @Privilege("MANTENIMIENTO_CONVOCATORIA")
  @PostMapping("/generateTokenForPrivatePromotion")
  @Operation(summary = "Generar token de promocion")
  public String generateTokenForPrivatePromotion(
          @Parameter(description = "ID de la promocion")@RequestParam Integer idPromocion) {
    return privatePromotionServices.generateTokenForPrivatePromotion(idPromocion);
  }

  @GetMapping("/validateTokenForMissingNotification/{token}")
  @Operation(summary = "Validar token de notificación faltante")
  public ResponseEntity<Response<MissingNotificationInfo>> validateTokenForMissingNotification(
      @Parameter(description = "Token a validar") @PathVariable("token") String token) throws Exception {
    MissingNotificationInfo info = formularioTokenService.validateTokenForMissingNotification(
        token);
    BaseResponse<MissingNotificationInfo> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Token validado", info);
  }

  @GetMapping("/validateTokenForPrivatePromotion/{token}")
  @Operation(summary = "Validar token de promocion")
  public ResponseEntity<Response<PrivatePromotionInfo>> validateTokenForPrivatePromotion(
          @Parameter(description = "Token a validar") @PathVariable("token") String token) throws Exception {
    PrivatePromotionInfo info = privatePromotionServices.validateTokenForPrivatePromotion(
            token);
    BaseResponse<PrivatePromotionInfo> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, "Token validado", info);
  }

  @Privilege("MANEJAR_SOLICITUD")
  @PostMapping("/generateTokenForEvaluation")
  @Operation(summary = "Generar token de evaluación")
  public String generateTokenForEvaluation(
      @Parameter(description = "Número de identificación") @RequestParam String numeroIdentificacion,
      @Parameter(description = "ID de Fase tipo prueba académica") @RequestParam Integer idFaseTipoPruebaAcademica) {
    return evaluationTokenService.generateTokenForEvaluation(numeroIdentificacion,
        idFaseTipoPruebaAcademica);
  }

  @GetMapping("/validateTokenForEvaluation/{token}")
  @Operation(summary = "Validar token de evaluación")
  public ResponseEntity<Response<EvaluationTokenInfo>> validateTokenForEvaluation(
      @Parameter(description = "Token a validar") @PathVariable("token") String token) {
    EvaluationTokenInfo info = evaluationTokenService.validateTokenForEvaluation(token);
    BaseResponse<EvaluationTokenInfo> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.CREATED, "Token validado", info);
  }

  @Privilege("MANEJAR_SOLICITUD")
  @PostMapping("/generateTokenForIncompleteApplication")
  @Operation(summary = "Generar token de aplicación incompleta")
  public String generateTokenForIncompleteApplication(
      @Parameter(description = "Número de identificación") @RequestParam String numeroIdentificacion,
      @Parameter(description = "ID de respuesta") @RequestParam String idRespuesta,
      @Parameter(description = "ID de solicitud") @RequestParam Integer idSolicitud) {
    return applicationTokenService.generateTokenForIncompleteApplication(numeroIdentificacion,
        idRespuesta, idSolicitud);
  }

  @GetMapping("/validateTokenForIncompleteApplication/{token}")
  @Operation(summary = "Validar token de aplicación incompleta")
  public ApplicationIncompleteInfo validateTokenForIncompleteApplication(
      @Parameter(description = "Token a validar") @PathVariable("token") String token) {
    return applicationTokenService.validateTokenForIncompleteApplication(token);
  }

  @Privilege("MANEJAR_SOLICITUD")
  @PostMapping("/generateTokenForInterview")
  @Operation(summary = "Generar token de entrevista")
  public String generateTokenForInterview(
      @Parameter(description = "Número de identificación") @RequestParam String numeroIdentificacion,
      @Parameter(description = "ID de Fase tipo entrevista") @RequestParam Integer idFaseTipoEntrevista,
      @Parameter(description = "Acción de correo") @RequestParam InterviewEmailAction action) {
    return interviewTokenService.generateTokenForInterview(numeroIdentificacion,
        idFaseTipoEntrevista, action);
  }

  @GetMapping("/validateTokenForInterview/{token}")
  @Operation(summary = "Validar token de entrevista")
  public ResponseEntity<Response<InterviewTokenInfo>> validateTokenForInterview(
      @Parameter(description = "Token a validar") @PathVariable("token") String token) {
    InterviewTokenInfo info = interviewTokenService.validateTokenForInterview(token);
    BaseResponse<InterviewTokenInfo> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.CREATED, "Token validado", info);
  }

  @Privilege("MANEJAR_SOLICITUD")
  @PostMapping("/generateTokenForPrivateBeca")
  @Operation(summary = "Generar token de beca privada")
  public String generateTokenForPrivateBeca(
      @Parameter(description = "Número de identificación") @RequestParam String numeroIdentificacion,
      @Parameter(description = "ID de promoción") @RequestParam Integer idPromocion,
      @Parameter(description = "Dirección de correo electrónico") @RequestParam String correo) {
    return becaPrivadaTokenService.generateTokenForPrivateBeca(numeroIdentificacion, correo,
        idPromocion);
  }

  @GetMapping("/validateTokenForPrivateBeca/{token}")
  @Operation(summary = "Validar token de beca privada")
  public ResponseEntity<Response<BecaPrivadaTokenInfo>> validateTokenForPrivateBeca(
      @Parameter(description = "Token a validar") @PathVariable("token") String token) {
    BecaPrivadaTokenInfo info = becaPrivadaTokenService.validateTokenForPrivateBeca(token);
    BaseResponse<BecaPrivadaTokenInfo> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.CREATED, "Token validado", info);
  }

}
