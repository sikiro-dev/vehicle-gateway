package com.sikiro.vehiclegateway.models;

import com.sikiro.vehiclegateway.models.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

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


}
