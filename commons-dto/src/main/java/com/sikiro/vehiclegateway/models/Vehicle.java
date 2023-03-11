package com.sikiro.vehiclegateway.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vehicle {
    public static final AttributeKey<Vehicle> VEHICLE_ATTRIBUTE_KEY = AttributeKey.newInstance("VEHICLE");


    private String id;

    private Double latitude;

    private Double longitude;

    private Status status;

    @JsonIgnore
    private Status desiredStatus;

    private Integer batteryLevel;
}
