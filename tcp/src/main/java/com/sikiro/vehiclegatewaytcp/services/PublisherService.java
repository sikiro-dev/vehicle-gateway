package com.sikiro.vehiclegatewaytcp.services;

import com.sikiro.vehiclegateway.models.Vehicle;

public interface PublisherService {

    void publish(Vehicle vehicle);
}
