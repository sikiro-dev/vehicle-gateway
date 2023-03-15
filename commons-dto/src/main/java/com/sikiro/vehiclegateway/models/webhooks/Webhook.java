package com.sikiro.vehiclegateway.models.webhooks;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@ToString
@RedisHash("webhook")
public class Webhook {

    private String id;

    @Indexed
    private String vehicleId;

    private String url;

}
