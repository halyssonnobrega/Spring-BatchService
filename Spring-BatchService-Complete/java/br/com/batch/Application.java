package br.com.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;

@EnableBatchProcessing
public class Application {

	public static void main(String[] args) {
	     System.exit(SpringApplication.exit(SpringApplication.run(
	             BatchConfiguration.class, args)));
	}

}
