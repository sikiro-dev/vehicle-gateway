package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegateway.models.webhooks.Webhook;
import com.sikiro.vehiclegatewayrest.repository.WebhookRepository;
import com.sikiro.vehiclegatewayrest.services.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookServiceImpl implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final WebClient webClient;


    @Override
    public void sendUpdate(Webhook webhook, Vehicle vehicle) {
        log.info("Sending update to: {}", webhook);
        webClient.post()
                .uri(webhook.getUrl())
                .bodyValue(vehicle)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    @Override
    public void createWebhook(Webhook webhook) {
        webhookRepository.save(webhook);
    }

    @Override
    public void deleteWebhook(String id) {
        webhookRepository.deleteById(id);
    }
}
