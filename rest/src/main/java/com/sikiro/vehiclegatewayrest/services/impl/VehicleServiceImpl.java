package com.sikiro.vehiclegatewayrest.services.impl;

import com.sikiro.vehiclegateway.models.Status;
import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegatewayrest.repositories.VehicleRepository;
import com.sikiro.vehiclegatewayrest.services.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> getVehicle(String id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public void sendUpdateFrequency(String id, int frequency) {
        //TODO WRITE ON REDIS
    }

    @Override
    public void sendCommand(String id, Status command) {
        //TODO WRITE ON REDIS
    }

    @Override
    public void disconnect(String id) {
        //TODO WRITE ON REDIS
    }
}
