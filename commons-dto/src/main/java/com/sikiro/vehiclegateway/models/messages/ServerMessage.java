package com.sikiro.vehiclegateway.models.messages;

import com.sikiro.vehiclegateway.models.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerMessage extends Message {

    private final String content;
    private final Status command;
    private final Integer frequency;

    public ServerMessage(Type type, String content) {
        super(type);
        this.content = content;
        this.command = null;
        this.frequency = null;
    }

    public ServerMessage(Type type, String content, Status command) {
        super(type);
        this.content = content;
        this.command = command;
        this.frequency = null;
    }

    public ServerMessage(Type type, String content, Integer frequency) {
        super(type);
        this.content = content;
        this.command = null;
        this.frequency = frequency;
    }
}
