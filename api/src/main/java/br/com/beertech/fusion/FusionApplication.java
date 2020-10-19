package br.com.beertech.fusion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class FusionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FusionApplication.class, args);
	}

}
