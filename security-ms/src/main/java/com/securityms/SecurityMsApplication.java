package com.securityms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableFeignClients
@SpringBootApplication
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
public class SecurityMsApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Panama"));
		SpringApplication.run(SecurityMsApplication.class, args);
	}

}
