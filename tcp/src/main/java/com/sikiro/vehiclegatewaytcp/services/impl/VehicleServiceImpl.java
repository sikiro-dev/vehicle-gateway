package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.Status;
import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegateway.models.messages.Message;
import com.sikiro.vehiclegateway.models.messages.Patterns;
import com.sikiro.vehiclegateway.models.messages.ServerMessage;
import com.sikiro.vehiclegatewaytcp.repositories.VehicleRepository;
import com.sikiro.vehiclegatewaytcp.server.MessageSenderHandler;
import com.sikiro.vehiclegatewaytcp.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final MessageSenderHandler messageSenderHandler;

    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicleRepository.deleteById(vehicle.getId());
    }

    @Override
    public void getUpdate(String vehicleId) {
        ServerMessage serverMessage = new ServerMessage(Message.Type.REPORT, Patterns.DATA_SERVER);
        messageSenderHandler.write(serverMessage, vehicleId);
    }

    @Override
    public void sendCommand(String vehicleId, Status command) {
        ServerMessage serverMessage = new ServerMessage(Message.Type.COMMAND,
                String.format(Patterns.COMMAND_SERVER, command.getCommand()), command);
        messageSenderHandler.write(serverMessage, vehicleId);
    }

    @Override
    public void setUpdateFrequency(String vehicleId, Integer frequency) {
        if (!(frequency == 0 || (frequency >= 10 && frequency <= 100))) {
            throw new IllegalArgumentException("Frequency must be between 10 and 100 or 0");
        }
        ServerMessage serverMessage = new ServerMessage(Message.Type.FREQUENCY,
                String.format(Patterns.FREQUENCY_SERVER, frequency), frequency);
        messageSenderHandler.write(serverMessage, vehicleId);
    }
}
