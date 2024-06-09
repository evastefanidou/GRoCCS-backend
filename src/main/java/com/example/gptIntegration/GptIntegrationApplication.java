package com.example.gptIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.example.controller", "com.example.model", "com.example.config"})
public class GptIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptIntegrationApplication.class, args);
		
	}

	
}
