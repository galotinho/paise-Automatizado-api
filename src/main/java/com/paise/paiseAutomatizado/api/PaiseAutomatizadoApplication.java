package com.paise.paiseAutomatizado.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PaiseAutomatizadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaiseAutomatizadoApplication.class, args);
	}

}
