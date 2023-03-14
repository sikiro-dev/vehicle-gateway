package com.sikiro.vehiclegateway.models.vehicles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sikiro.vehiclegateway.models.messages.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vehicle {

    private String id;

    private Double latitude;

    private Double longitude;

    private Status status;

    private Integer batteryLevel;

    @JsonIgnore
    private Status desiredStatus;

    @JsonIgnore
    private Message.Type lastMessage;

}
