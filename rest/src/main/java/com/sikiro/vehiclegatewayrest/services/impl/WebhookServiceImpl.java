package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegateway.models.vehicles.VehicleEvent;
import com.sikiro.vehiclegateway.models.webhooks.Webhook;
import com.sikiro.vehiclegatewayrest.repository.WebhookRepository;
import com.sikiro.vehiclegatewayrest.services.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookServiceImpl implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final WebClient webClient;


    @Override
    public void sendUpdate(Webhook webhook, Vehicle vehicle) {
        log.info("Sending update to: {}", webhook.getId());
        List<VehicleEvent> vehicleEvents = VehicleEvent.from(vehicle);
        webClient.post()
                .uri(webhook.getUrl())
                .bodyValue(vehicleEvents)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe(
                        success -> log.info("Successfully sent update to: {}, with updates {}", webhook.getId(), vehicleEvents),
                        error -> log.error("Error sending update to: {}", webhook.getId())
                );
    }

    @Override
    public void createWebhook(Webhook webhook) {
        webhookRepository.save(webhook);
    }

    @Override
    public void deleteWebhook(String id) {
        webhookRepository.deleteById(id);
    }

    @Override
    public Iterable<Webhook> getWebhooks() {
        return webhookRepository.findAll();
    }
}
