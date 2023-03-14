package com.sikiro.vehiclegatewayrest.controllers.impl;

import com.sikiro.vehiclegateway.models.webhooks.Webhook;
import com.sikiro.vehiclegatewayrest.controllers.Controller;
import com.sikiro.vehiclegatewayrest.services.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/webhook")
@RequiredArgsConstructor
public class WebhookController extends Controller {

    private final WebhookService webhookService;

    @PostMapping
    public ResponseEntity<Void> createWebhook(@RequestBody Webhook webhook) {
        log.info("Request create webhook for: {}", webhook);
        webhookService.createWebhook(webhook);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteWebhook(@PathVariable String id) {
        log.info("Request delete webhook for: {}", id);
        webhookService.deleteWebhook(id);
        return ResponseEntity.ok().build();
    }


}
