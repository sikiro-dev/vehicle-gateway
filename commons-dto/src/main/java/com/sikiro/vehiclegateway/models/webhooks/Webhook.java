package com.sikiro.vehiclegateway.models.webhooks;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@ToString
@RedisHash
public class Webhook {

    @Id
    private String vehicleId;

    private String url;


    @Override
    public int hashCode() {
        return vehicleId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof Webhook other) {
            return other.vehicleId.equals(this.vehicleId);
        }
        return false;
    }
}
