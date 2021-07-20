package com.projeto.generico;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CommandLineRunner para permitir executar uma ação quando a aplicação iniciar

@SpringBootApplication
public class OntologyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(OntologyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}