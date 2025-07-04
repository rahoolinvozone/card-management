package com.fintech.cms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Card Management API", version = "1.0", description = "API for managing cards, accounts, and transactions"))
@EnableFeignClients(basePackages = "com.fintech.cms.controller")
public class CardManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardManagementSystemApplication.class, args);
	}

}
