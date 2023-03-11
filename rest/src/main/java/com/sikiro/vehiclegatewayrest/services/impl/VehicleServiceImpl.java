package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegatewayrest.services.VehicleService;
import com.sikiro.vehiclegatewaytcp.models.Status;
import com.sikiro.vehiclegatewaytcp.models.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Override
    public Optional<Vehicle> getVehicle(String id) {
        return Optional.empty();
    }

    @Override
    public void sendUpdateFrequency(String id, int frequency) {

    }

    @Override
    public void sendCommand(String id, Status command) {

    }

    @Override
    public void disconnect(String id) {

    }
}
