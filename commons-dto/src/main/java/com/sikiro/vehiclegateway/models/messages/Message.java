package com.sikiro.vehiclegateway.models.messages;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public abstract class Message {

    private final String id;

    private final Type type;

    protected Message(Type type) {
        this.id = java.util.UUID.randomUUID().toString();
        this.type = type;
    }

    public enum Type {
        HELLO,
        HEARTBEAT,
        DATA,
        REPORT,
        REPORT_ACK,
        COMMAND,
        GOODBYE_REQUEST,
        GOODBYE_ACK,
        ERROR
    }

}

