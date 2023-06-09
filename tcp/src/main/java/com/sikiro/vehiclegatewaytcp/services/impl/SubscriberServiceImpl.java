package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.messages.Message;
import com.sikiro.vehiclegatewaytcp.server.ChannelRepository;
import com.sikiro.vehiclegatewaytcp.server.MessageSenderHandler;
import com.sikiro.vehiclegatewaytcp.services.SubscriberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final ReactiveRedisOperations<String, Message> messageTemplate;
    private final ChannelRepository channelRepository;
    private final MessageSenderHandler messageSenderHandler;

    @Value("${redis.server_messages_channel:server_messages}")
    private String messageChannel;

    @Override
    @PostConstruct
    public void subscribe() {
        messageTemplate.listenToChannel(messageChannel)
                .map(ReactiveSubscription.Message::getMessage)
                .filter(message -> channelRepository.containsKey(message.getData().getId()))
                .subscribe(message -> {
                    log.info("Received message: {}", message);
                    messageSenderHandler.write(message);
                });
    }

}
