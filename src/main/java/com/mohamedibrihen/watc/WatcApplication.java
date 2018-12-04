package com.mohamedibrihen.watc;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WatcApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatcApplication.class, args);
	}

	// TODO Move this to a dedicated configuration class.
	@Bean
	public WebClient getWebClient() {
		return new WebClient();
	}
}
