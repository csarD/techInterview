package com.securityms.controller;

import com.securityms.controller.request.UpdateNewPasswordRequestDTO;
import com.securityms.controller.request.UpdatePasswordRequestDTO;
import com.securityms.controller.request.UsuarioRequestDTO;
import com.securityms.controller.request.ValidatePasswordUpdateCodeRequestDTO;
import com.securityms.controller.response.BaseResponse;
import com.securityms.controller.response.Response;
import com.securityms.controller.response.UsuarioResponseDTO;
import com.securityms.repository.dto.UsuarioRolRelacionDTO;
import com.securityms.service.IUsuarioTemporalService;
import com.securityms.service.UsuarioService;
import com.securityms.util.privilege.Privilege;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsuarioController {

    private final UsuarioService service;
    private final IUsuarioTemporalService usuarioTemporalService;

    @Privilege("MANTENIMIENTO_USUARIO")
    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Response<UsuarioResponseDTO>> findById(
            @Parameter(description = "ID del usuario") @PathVariable("id") final Integer id) {
        UsuarioResponseDTO usuario = service.findById(id);
        BaseResponse<UsuarioResponseDTO> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.OK, "Usuario encontrado.", usuario);
    }

    @GetMapping("/identificacion/{identificacion}")
    @Operation(summary = "Obtener un usuario por identificación")
    public ResponseEntity<Response<UsuarioResponseDTO>> findByIdentificacion
            (@Parameter(description = "Identificación del usuario")
             @PathVariable("identificacion") String identificacion) {

        BaseResponse<UsuarioResponseDTO> response = new BaseResponse<>();
        UsuarioResponseDTO usuario = service.findByIdentification(identificacion);
        if (usuario != null) {
            return response.buildResponseEntity(HttpStatus.OK, "Usuario encontrado.", usuario);
        }
        return response.buildResponseEntity(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {

        List<UsuarioResponseDTO> usuarioList = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @Operation(summary = "Obtener todos los usuarios de manera paginada")
    @GetMapping("/findAll/paged")
    public ResponseEntity<Page<UsuarioResponseDTO>> findAll(
            @Parameter(description = "Número de página") @RequestParam Integer page,
            @Parameter(description = "Tamaño de página") @RequestParam Integer size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<UsuarioResponseDTO> usuarioList = service.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @GetMapping("/findByRole/{idRole}")
    @Operation(summary = "Obtener usuarios por rol")
    public ResponseEntity<Response<List<UsuarioResponseDTO>>> findByRole(
            @Parameter(description = "ID rol del usuario") @PathVariable("idRole") Integer idRole) {

        BaseResponse<List<UsuarioResponseDTO>> response = new BaseResponse<>();
        List<UsuarioResponseDTO> usuarioList = service.findByRole(idRole);
        return response.buildResponseEntity(HttpStatus.OK, "Usuarios encontrados", usuarioList);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @GetMapping("/findByRole/{idRole}/paged")
    @Operation(summary = "Obtener usuarios por rol de manera paginada")
    public ResponseEntity<Response<Page<UsuarioResponseDTO>>> findByRole(
            @Parameter(description = "ID rol del usuario") @PathVariable("idRole") Integer idRole,
            @Parameter(description = "Número de página") @RequestParam Integer page,
            @Parameter(description = "Tamaño de página") @RequestParam Integer size) {

        PageRequest pageable = PageRequest.of(page, size);

        BaseResponse<Page<UsuarioResponseDTO>> response = new BaseResponse<>();
        Page<UsuarioResponseDTO> usuarioList = service.findByRole(idRole, pageable);
        return response.buildResponseEntity(HttpStatus.OK, "Usuarios encontrados", usuarioList);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @PostMapping
    @Operation(summary = "Guardar un usuario")
    public ResponseEntity<Response<UsuarioResponseDTO>> saveUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a guardar")
            @RequestBody @Valid UsuarioRequestDTO request) {

        BaseResponse<UsuarioResponseDTO> response = new BaseResponse<>();
        try {
            UsuarioResponseDTO usuario = service.saveUser(request);
            usuarioTemporalService.deleteTemporalUserIfExists(request.getUsuario());
            return response.buildResponseEntity(HttpStatus.CREATED, "Usuario guardado con exito", usuario);
        } catch (EntityExistsException e) {
            return response.buildResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @DeleteMapping
    @Operation(summary = "Eliminar un usuario por ID")
    public ResponseEntity<Response<Void>> deleteById(
            @Parameter(description = "ID del usuario") @RequestParam final Integer userId) {
        service.deleteUserById(userId);
        BaseResponse<Void> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.OK, "Usuario eliminado con exito", null);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @PutMapping
    @Operation(summary = "Actualizar un usuario")
    public ResponseEntity<Response<UsuarioResponseDTO>> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a actualizar")
            @RequestBody @Valid UsuarioRequestDTO request) {
        UsuarioResponseDTO usuario = service.updateUser(request);
        BaseResponse<UsuarioResponseDTO> response = new BaseResponse<>();
        return response.buildResponseEntity(HttpStatus.CREATED, "Usuario actualizo con exito", usuario);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @PutMapping("/changeRol")
    @Operation(summary = "Cambiar rol a un usuario")
    public ResponseEntity<Response<Void>> changeRol(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del rol a cambiar")
            @RequestBody @Valid UsuarioRolRelacionDTO request) {
        BaseResponse<Void> response = new BaseResponse<>();
        service.changeRol(request);
        return response.buildResponseEntity(HttpStatus.OK, "Rol actualizado para el usuario.", null);
    }

    @PutMapping("/updateNewPassword")
    @Operation(summary = "Cambiar la contraseña la primera vez")
    public ResponseEntity<Response<Void>> updateNewPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la nueva contraseña")
            @RequestBody @Valid UpdateNewPasswordRequestDTO request) throws Exception {
        BaseResponse<Void> response = new BaseResponse<>();
        service.updateNewPassword(request);
        return response.buildResponseEntity(HttpStatus.OK, "Primera contraseña actualizada.", null);
    }

    @Privilege("MANTENIMIENTO_USUARIO")
    @PutMapping("/disableUser/{username}")
    @Operation(summary = "Deshabilitar un usuario")
    public ResponseEntity<Response<Void>> disableUser(
            @Parameter(description = "Username") @PathVariable("username") String username) {
        BaseResponse<Void> response = new BaseResponse<>();
        service.disableUser(username);
        return response.buildResponseEntity(HttpStatus.OK, "Usuario desactivado.", null);
    }

    @PutMapping("/generatePasswordUpdateCode/{username}")
    @Operation(summary = "Generar código para cambio de contraseña")
    public ResponseEntity<Response<Void>> generatePasswordUpdateCode(
            @Parameter(description = "Username") @PathVariable("username") String username) {
        BaseResponse<Void> response = new BaseResponse<>();
        service.generatePasswordUpdateCode(username);
        return response.buildResponseEntity(HttpStatus.OK, "Correo para cambio de contraseña enviado.",
                null);
    }

    @PutMapping("/validatePasswordUpdateCode")
    @Operation(summary = "Validar código para cambio de contraseña")
    public ResponseEntity<Response<Void>> validatePasswordUpdateCode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de código de validación")
            @RequestBody @Valid ValidatePasswordUpdateCodeRequestDTO request) throws Exception {
        BaseResponse<Void> response = new BaseResponse<>();
        service.validatePasswordUpdateCode(request);
        return response.buildResponseEntity(HttpStatus.OK, "Codigo para cambio de contraseña validado.",
                null);
    }

    @PutMapping("/updatePassword")
    @Operation(summary = "Actualizar contraseña")
    public ResponseEntity<Response<Void>> updatePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de nueva contraseña")
            @RequestBody @Valid UpdatePasswordRequestDTO request) throws Exception {
        BaseResponse<Void> response = new BaseResponse<>();
        service.updatePassword(request);
        return response.buildResponseEntity(HttpStatus.OK, "Contraseña actualizada.", null);
    }
}
