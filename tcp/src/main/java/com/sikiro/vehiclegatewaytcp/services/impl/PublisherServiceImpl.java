package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegatewaytcp.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final ReactiveRedisOperations<String, Vehicle> vehicleTemplate;

    @Value("${redis.vehicles_channel:vehicles}")
    private String channel;

    @Override
    public void publish(Vehicle vehicle) {
        vehicleTemplate.convertAndSend(channel, vehicle).subscribe();
    }
}
