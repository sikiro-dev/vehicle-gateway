package com.sikiro.vehiclegatewaytcp.services;


import com.sikiro.vehiclegateway.models.Status;
import com.sikiro.vehiclegateway.models.Vehicle;

public interface VehicleService {

    void save(Vehicle vehicle);

    void delete(Vehicle vehicle);

    void getUpdate(String vehicleId);

    void sendCommand(String vehicleId, Status command);

    void setUpdateFrequency(String vehicleId, Integer frequency);
}
