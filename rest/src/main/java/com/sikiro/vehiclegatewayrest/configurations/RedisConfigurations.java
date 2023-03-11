package com.sikiro.vehiclegatewayrest.configurations;

import com.sikiro.vehiclegateway.models.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfigurations {

    @Bean
    public ReactiveRedisOperations<String, Vehicle> vehicleTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisSerializer<Vehicle> valueSerializer = new Jackson2JsonRedisSerializer<>(Vehicle.class);
        RedisSerializationContext<String, Vehicle> serializationContext = RedisSerializationContext.<String, Vehicle>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

}
