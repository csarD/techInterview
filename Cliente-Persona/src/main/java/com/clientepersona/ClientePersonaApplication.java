package com.clientepersona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ClientePersonaApplication {

	public static void main(String[] args) {

		SpringApplication.run(ClientePersonaApplication.class, args);
	}

}
