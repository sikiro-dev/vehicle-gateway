package com.sikiro.vehiclegatewaytcp.configurations;

import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegateway.models.messages.ServerMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfigurations {

    @Value("${spring.data.redis.writer.host}")
    private String writerHost;

    @Value("${spring.data.redis.writer.port}")
    private int writerPort;

    @Value("${spring.data.redis.reader.host}")
    private String readerHost;

    @Value("${spring.data.redis.reader.port}")
    private int readerPort;

    @Bean
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactoryWriter() {
        return new LettuceConnectionFactory(writerHost, writerPort);
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactoryReader() {
        return new LettuceConnectionFactory(readerHost, readerPort);
    }

    @Bean
    public ReactiveRedisOperations<String, ServerMessage> messageTemplate(LettuceConnectionFactory lettuceConnectionFactoryReader) {
        RedisSerializer<ServerMessage> valueSerializer = new Jackson2JsonRedisSerializer<>(ServerMessage.class);
        RedisSerializationContext<String, ServerMessage> serializationContext = RedisSerializationContext.<String, ServerMessage>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactoryReader, serializationContext);
    }

    @Bean
    public ReactiveRedisOperations<String, Vehicle> vehicleTemplate(LettuceConnectionFactory lettuceConnectionFactoryWriter) {
        RedisSerializer<Vehicle> valueSerializer = new Jackson2JsonRedisSerializer<>(Vehicle.class);
        RedisSerializationContext<String, Vehicle> serializationContext = RedisSerializationContext.<String, Vehicle>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactoryWriter, serializationContext);
    }

}
