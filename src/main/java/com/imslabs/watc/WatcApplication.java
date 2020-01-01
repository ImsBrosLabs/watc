package com.imslabs.watc;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WatcApplication {
	public static void main(String[] args) {
		SpringApplication.run(WatcApplication.class, args);
	}

}
