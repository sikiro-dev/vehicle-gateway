package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegateway.models.vehicles.Status;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegateway.models.messages.Patterns;
import com.sikiro.vehiclegateway.models.messages.ServerMessage;
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

    private final ReactiveRedisOperations<String, ServerMessage> messageTemplate;
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
        ServerMessage serverMessage = new ServerMessage(ServerMessage.Type.REPORT,
                Patterns.DATA_SERVER, id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }

    @Override
    public void sendUpdateFrequency(String id, int frequency) {
        ServerMessage serverMessage = new ServerMessage(ServerMessage.Type.FREQUENCY,
                String.format(Patterns.FREQUENCY_SERVER, frequency), frequency, id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }

    @Override
    public void sendCommand(String id, Status command) {
        ServerMessage serverMessage = new ServerMessage(ServerMessage.Type.COMMAND,
                String.format(Patterns.COMMAND_SERVER, command.getCommand()), command, id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }

    @Override
    public void disconnect(String id) {
        ServerMessage serverMessage = new ServerMessage(ServerMessage.Type.GOODBYE_REQUEST,
                Patterns.GOODBYE_REQUEST_STRING, id);
        messageTemplate.convertAndSend(serverMessagesChannel, serverMessage).subscribe();
    }
}
