package com.sikiro.vehiclegatewayrest.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebhookConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

}
