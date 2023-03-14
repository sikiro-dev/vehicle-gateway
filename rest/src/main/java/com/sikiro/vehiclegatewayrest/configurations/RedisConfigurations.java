package com.sikiro.vehiclegatewayrest.configurations;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RedisConfigurations {

    private final StreamListener<String, ObjectRecord<String, Vehicle>> streamListener;

    @Value("${redis.vehicles_channel:vehicles}")
    private String vehiclesChannel;

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Vehicle>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(1))
                        .targetType(Vehicle.class)
                        .build();
        StreamMessageListenerContainer<String, ObjectRecord<String, Vehicle>> listenerContainer = StreamMessageListenerContainer
                .create(redisConnectionFactory, options);
        Subscription subscription = listenerContainer.receive(
                Consumer.from(vehiclesChannel, InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(vehiclesChannel, ReadOffset.lastConsumed()),
                streamListener);
        listenerContainer.start();
        return subscription;
    }


}
