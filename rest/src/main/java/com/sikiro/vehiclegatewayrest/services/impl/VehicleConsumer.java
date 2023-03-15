package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegatewayrest.repository.WebhookRepository;
import com.sikiro.vehiclegatewayrest.services.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleConsumer implements StreamListener<String, ObjectRecord<String, Vehicle>> {

    private final WebhookRepository webhookRepository;
    private final WebhookService webhookService;

    @Override
    public void onMessage(ObjectRecord<String, Vehicle> message) {
        log.info("Received message: {}", message);
        webhookRepository.findAllByVehicleId(message.getValue().getId())
                .forEach(webhook -> webhookService.sendUpdate(webhook, message.getValue()));
    }

}