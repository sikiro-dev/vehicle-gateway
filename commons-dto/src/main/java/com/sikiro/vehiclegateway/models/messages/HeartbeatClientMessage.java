package com.sikiro.vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartbeatClientMessage extends ClientMessage {

    public HeartbeatClientMessage() {
        super(Type.HEARTBEAT);
    }

}
