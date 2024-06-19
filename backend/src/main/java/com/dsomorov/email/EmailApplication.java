package com.dsomorov.email;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		
		System.setProperty("POSTGRES_URL", dotenv.get("POSTGRES_URL"));
		System.setProperty("POSTGRES_USERNAME", dotenv.get("POSTGRES_USERNAME"));
		System.setProperty("POSTGRES_PASSWORD", dotenv.get("POSTGRES_PASSWORD"));
		
		SpringApplication.run(EmailApplication.class, args);
	}

}
