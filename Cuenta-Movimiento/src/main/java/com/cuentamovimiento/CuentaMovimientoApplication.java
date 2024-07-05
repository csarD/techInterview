package com.cuentamovimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CuentaMovimientoApplication {

	public static void main(String[] args) {

		SpringApplication.run(CuentaMovimientoApplication.class, args);
	}

}
