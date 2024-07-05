package com.cuentamovimiento.controller;

import com.cuentamovimiento.controller.DTO.Reporte;
import com.cuentamovimiento.controller.DTO.ResumenCuentas;
import com.cuentamovimiento.controller.request.CuentaRequestDTO;
import com.cuentamovimiento.controller.response.BaseResponse;
import com.cuentamovimiento.controller.response.Response;
import com.cuentamovimiento.repository.domain.Cuenta;
import com.cuentamovimiento.repository.domain.Movimiento;
import com.cuentamovimiento.service.implementation.CuentaServiceImpl;
import com.cuentamovimiento.service.implementation.MovimientoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

  @Autowired
  private CuentaServiceImpl cuentaService;
  @Autowired
  private MovimientoServiceImpl movimientoService;

  @GetMapping()
  @Operation(summary = "Obtener datos")
  public ResponseEntity<Response<Map<String,Object>>> PersonAccount(

          @RequestParam Instant fechaInicial, @RequestParam Instant fechaFinal, @RequestParam String idCliente) {
    Map<String,Object> payload = new HashMap<String,Object>();
  String result = "";
    List<Integer> data=cuentaService.getAllClientAccounts(Integer.parseInt(idCliente));
    if(data.size()==0){
      result = "Cliente no posee cuentas";
    }else{
      Cuenta a= cuentaService.get(data.get(0));
      String nombre=a.getCliente().getPersonaid().getNombre();

      List<Reporte> dataToUser = new ArrayList<>();
      List<ResumenCuentas> resumenCuentas = new ArrayList<>();
      data.forEach(account->{

        Cuenta cu = cuentaService.get(account);
        ResumenCuentas resumenCuenta= new ResumenCuentas(cu.getId(),cu.getSaldo());
        resumenCuentas.add(resumenCuenta);
        List<Movimiento> datos= movimientoService.getAllMovesfromAccount(account);
        datos.forEach(movement->{
          Instant fecha = movement.getFecha();
          if(fecha.isAfter(fechaInicial)&& fecha.isBefore(fechaFinal)){
            Reporte report = new Reporte();
            report.setFecha(fecha);
            report.setMovimiento(movement.getValor());
            report.setNombreCliente(nombre);
            report.setNumeroCuenta(cu.getId());
            report.setTipoProducto(cu.getTipo().getNombre());
            report.setStatus(cu.getEstado());
            report.setSaldoActual(movement.getSaldo());
            report.setSaldoInicial(movement.getSaldo()- movement.getValor());
            dataToUser.add(report);
          }
        });

      });
      payload.put("Cuentas",resumenCuentas);
      payload.put("Movimientos",dataToUser);


    }


    BaseResponse<Map<String,Object>> response = new BaseResponse<>();
    return response.buildResponseEntity(HttpStatus.OK, result,
            payload);
  }


}
