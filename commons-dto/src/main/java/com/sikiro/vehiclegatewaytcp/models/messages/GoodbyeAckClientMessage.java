package com.sikiro.vehiclegatewaytcp.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodbyeAckClientMessage extends ClientMessage {

    public GoodbyeAckClientMessage() {
        super(Type.GOODBYE_ACK);
    }

}
