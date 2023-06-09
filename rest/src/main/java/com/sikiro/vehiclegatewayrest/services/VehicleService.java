package com.sikiro.vehiclegatewayrest.services;

import com.sikiro.vehiclegateway.models.vehicles.Status;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import reactor.core.publisher.Mono;

public interface VehicleService {

    Mono<Vehicle> getVehicle(String id);

    void getUpdate(String id);

    void sendUpdateFrequency(String id, int frequency);

    void sendCommand(String id, Status command);

    void disconnect(String id);

}
