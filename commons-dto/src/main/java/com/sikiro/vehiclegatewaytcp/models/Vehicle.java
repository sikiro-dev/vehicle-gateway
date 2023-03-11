package com.sikiro.vehiclegatewaytcp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Vehicle {

    private UUID id;

    private Double latitude;

    private Double longitude;

    private Status status;

    private Integer batteryLevel;


}
