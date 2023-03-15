package com.sikiro.vehiclegateway.models.vehicles;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class VehicleEvent {

    private String vehicleId;

    private Event event;

    private Map<String, Object> parameters;

    public static List<VehicleEvent> from(Vehicle vehicle) {
        return vehicle.getLastEvents().stream().map(event -> {
            VehicleEvent vehicleEvent = new VehicleEvent();
            vehicleEvent.setVehicleId(vehicle.getId());
            vehicleEvent.setEvent(event);
            vehicleEvent.setParameters(new HashMap<>());
            switch (event) {
                case CONNECTED, DISCONNECTED:
                    break;
                case STATUS_CHANGED:
                    vehicleEvent.getParameters().put("status", vehicle.getStatus());
                    break;
                case BATTERY_LEVEL_CHANGED:
                    vehicleEvent.getParameters().put("batteryLevel", vehicle.getBatteryLevel());
                    break;
                case LOCATION_CHANGED:
                    vehicleEvent.getParameters().put("latitude", vehicle.getLatitude());
                    vehicleEvent.getParameters().put("longitude", vehicle.getLongitude());
                    break;
            }
            return vehicleEvent;
        }).toList();
    }


}
