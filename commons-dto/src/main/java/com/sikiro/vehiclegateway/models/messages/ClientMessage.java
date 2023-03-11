package com.sikiro.vehiclegateway.models.messages;

public abstract class ClientMessage extends Message {

    public ClientMessage(Message.Type type) {
        super(type);
    }
}
