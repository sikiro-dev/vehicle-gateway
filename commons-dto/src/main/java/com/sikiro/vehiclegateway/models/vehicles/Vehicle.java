package com.sikiro.vehiclegateway.models.vehicles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Setter
@ToString
@RedisHash
public class Vehicle {

    private String id;

    private Double latitude;

    private Double longitude;

    private Status status;

    private Integer batteryLevel;

    @JsonIgnore
    private List<Event> lastEvents;

}
