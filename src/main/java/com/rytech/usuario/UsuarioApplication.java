package com.rytech.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UsuarioApplication {

	public static void main(String[] args) {
		System.out.println("TESTE DB_NAME: " + System.getenv("DB_NAME"));
		SpringApplication.run(UsuarioApplication.class, args);
	}

}
