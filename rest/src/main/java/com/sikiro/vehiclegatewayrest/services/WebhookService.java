package com.sikiro.vehiclegatewayrest.services;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegateway.models.webhooks.Webhook;

public interface WebhookService {

    void sendUpdate(Webhook webhook, Vehicle vehicle);

    void createWebhook(Webhook webhook);

    void deleteWebhook(String id);
}
