package com.sikiro.vehiclegatewaytcp.configurations;

import com.sikiro.vehiclegateway.models.messages.Message;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegateway.models.webhooks.Webhook;
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
    public ReactiveRedisOperations<String, Message> messageTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<Message> valueSerializer = new Jackson2JsonRedisSerializer<>(Message.class);
        RedisSerializationContext<String, Message> serializationContext = RedisSerializationContext.<String, Message>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisOperations<String, Vehicle> vehicleTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<Vehicle> valueSerializer = new Jackson2JsonRedisSerializer<>(Vehicle.class);
        RedisSerializationContext<String, Vehicle> serializationContext = RedisSerializationContext.<String, Vehicle>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisOperations<String, Webhook> webhookTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<Webhook> valueSerializer = new Jackson2JsonRedisSerializer<>(Webhook.class);
        RedisSerializationContext<String, Webhook> serializationContext = RedisSerializationContext.<String, Webhook>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisOperations<String, String> stringTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, RedisSerializationContext.string());
    }


}
