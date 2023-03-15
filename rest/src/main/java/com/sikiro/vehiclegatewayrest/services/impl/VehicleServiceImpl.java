package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegateway.models.messages.Message;
import com.sikiro.vehiclegateway.models.vehicles.Status;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegatewayrest.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final ReactiveRedisOperations<String, Message> messageTemplate;
    private final ReactiveRedisOperations<String, Vehicle> vehicleTemplate;

    @Value("${redis.server_messages_channel:server_messages}")
    private String serverMessagesChannel;

    @Value("${redis.vehicles_channel:vehicles}")
    private String vehiclesChannel;

    @Override
    public Mono<Vehicle> getVehicle(String id) {
        return vehicleTemplate.listenTo(ChannelTopic.of(vehiclesChannel))
                .filter(message -> message.getMessage().getId().equals(id))
                .map(ReactiveSubscription.Message::getMessage)
                .next();
    }

    @Override
    public void getUpdate(String id) {
        Message serverMessage = Message.fromServer(Message.Type.DATA,
                Message.Type.DATA.getServer(), id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }

    @Override
    public void sendUpdateFrequency(String id, int frequency) {
        Message serverMessage = Message.fromServer(Message.Type.FREQUENCY,
                String.format(Message.Type.FREQUENCY.getServer(), frequency), id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }

    @Override
    public void sendCommand(String id, Status command) {
        Message serverMessage = Message.fromServer(Message.Type.COMMAND,
                String.format(Message.Type.COMMAND.getServer(), command.getCommand()), id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }

    @Override
    public void disconnect(String id) {
        Message serverMessage = Message.fromServer(Message.Type.GOODBYE_REQUEST,
                Message.Type.GOODBYE_REQUEST.getServer(), id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }
}
