package com.sikiro.vehiclegatewaytcp.repositories;

import com.sikiro.vehiclegateway.models.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, String> {
}
