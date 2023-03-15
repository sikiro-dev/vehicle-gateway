package com.sikiro.vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodbyeAckClientMessage extends ClientMessage {

    public GoodbyeAckClientMessage() {
        super(Type.GOODBYE_ACK);
    }

}
