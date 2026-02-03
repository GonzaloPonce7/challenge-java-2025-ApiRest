package com.challenge.puntosdeventa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.challenge.puntosdeventa.repository")
@EnableCaching
public class PuntosdeventaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntosdeventaApplication.class, args);
	}

}
