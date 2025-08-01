package com.sosaw.sosaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SosawApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosawApplication.class, args);
	}

}
