package com.sikiro.vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloClientMessage extends ClientMessage {

    private final String deviceId;

    public HelloClientMessage(String deviceId) {
        super(Type.HELLO);
        this.deviceId = deviceId;
    }

}
