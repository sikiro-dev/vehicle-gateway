package com.sikiro.vehiclegateway.models.messages;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public abstract class Message {

    private final String id;

    private Type type;

    protected Message(Type type) {
        this();
        this.type = type;
    }

    protected Message() {
        this.id = java.util.UUID.randomUUID().toString();
    }

    public enum Type {
        HELLO,
        HEARTBEAT,
        DATA,
        FREQUENCY,
        REPORT,
        REPORT_ACK,
        COMMAND,
        GOODBYE_REQUEST,
        GOODBYE_ACK,
        ERROR
    }

}

