package vehiclegateway.services.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import vehiclegateway.models.Vehicle;
import vehiclegateway.services.PublisherService;

import java.util.UUID;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final ReactiveRedisOperations<String, Vehicle> vehicleTemplate;

    @Value("${topic.name:vehicle}")
    private String channel;

    public PublisherServiceImpl(ReactiveRedisOperations<String, Vehicle> vehicleTemplate) {
        this.vehicleTemplate = vehicleTemplate;
    }

    @Override
    public void publish(Vehicle vehicle) {
        vehicleTemplate.convertAndSend(channel, vehicle);
    }

    @PostConstruct
    public void init(){
        System.out.println("channel: " + channel);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setLatitude(1.0);
        vehicle.setLongitude(-1.0);
        vehicle.setBatteryLevel(42);
        publish(vehicle);
    }

}
