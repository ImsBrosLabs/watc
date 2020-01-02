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

        // Disable JS + CSS for a faster page loading.
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        // This will prevent the app from failing when having "non distructive" errors like a 520 http error) + ignorring script errors.
        clientOptions.setThrowExceptionOnFailingStatusCode(false);
        clientOptions.setThrowExceptionOnScriptError(false);

        return client;
    }

}