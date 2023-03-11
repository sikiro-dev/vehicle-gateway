package com.sikiro.vehiclegatewayrest.services;

import com.sikiro.vehiclegatewaytcp.models.Status;
import com.sikiro.vehiclegatewaytcp.models.Vehicle;

import java.util.Optional;

public interface VehicleService {

    Optional<Vehicle> getVehicle(String id);

    void sendUpdateFrequency(String id, int frequency);

    void sendCommand(String id, Status command);

    void disconnect(String id);

}
