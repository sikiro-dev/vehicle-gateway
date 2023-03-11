package com.sikiro.vehiclegatewayrest.configurations;

import com.sikiro.vehiclegateway.models.messages.ClientMessage;
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
    public ReactiveRedisOperations<String, ClientMessage> messageTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisSerializer<ClientMessage> valueSerializer = new Jackson2JsonRedisSerializer<>(ClientMessage.class);
        RedisSerializationContext<String, ClientMessage> serializationContext = RedisSerializationContext.<String, ClientMessage>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }

}
