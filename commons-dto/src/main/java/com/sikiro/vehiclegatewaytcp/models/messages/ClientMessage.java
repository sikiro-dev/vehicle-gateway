package com.sikiro.vehiclegatewaytcp.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ClientMessage extends Message {

    public ClientMessage(Message.Type type) {
        super(type);
    }
}
