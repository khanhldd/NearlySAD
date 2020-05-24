package com.flywithmet7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class AppBookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppBookingApplication.class, args);
	}
}

@Configuration
class RestTemplateCofig {
	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced // Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
		return new RestTemplate();

	}
}