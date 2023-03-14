package com.sikiro.vehiclegatewaytcp.services;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;

public interface PublisherService {

    void publish(Vehicle vehicle);
}
