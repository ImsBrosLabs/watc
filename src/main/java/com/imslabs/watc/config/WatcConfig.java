package com.imslabs.watc.config;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatcConfig {

    @Bean
    WebClient getCustomwebClient() {
        WebClient client = new WebClient();

        WebClientOptions clientOptions = client.getOptions();

        clientOptions.setThrowExceptionOnFailingStatusCode(false);
        clientOptions.setThrowExceptionOnScriptError(false);

        return client;
    }

}