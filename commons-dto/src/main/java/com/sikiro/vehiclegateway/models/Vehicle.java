package com.sikiro.vehiclegateway.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@ToString
@RedisHash
public class Vehicle {
    public static final AttributeKey<Vehicle> VEHICLE_ATTRIBUTE_KEY = AttributeKey.newInstance("VEHICLE");


    private String id;

    private Double latitude;

    private Double longitude;

    private Status status;

    @JsonIgnore
    private Status desiredStatus;

    private Integer batteryLevel;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Vehicle vehicle))
            return false;
        return vehicle.getId().equals(this.getId());
    }
}
