package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegatewaytcp.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
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
        ObjectRecord<String, Vehicle> record = ObjectRecord.create(channel, vehicle);
        vehicleTemplate.opsForStream().add(record).subscribe();
        vehicleTemplate.convertAndSend(channel, vehicle).subscribe();
    }
}
