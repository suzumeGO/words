package com.example.wordstraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableJpaRepositories
@SpringBootApplication
public class WordsTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordsTrainingApplication.class, args);
	}

}
