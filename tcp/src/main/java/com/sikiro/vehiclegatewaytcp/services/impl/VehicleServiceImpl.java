package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegatewaytcp.repositories.VehicleRepository;
import com.sikiro.vehiclegatewaytcp.services.VehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
