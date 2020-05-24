package com.flywithmet7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BBATicketApplication {
	public static void main(String[] args) {
		SpringApplication.run(BBATicketApplication.class, args);
	}

}
