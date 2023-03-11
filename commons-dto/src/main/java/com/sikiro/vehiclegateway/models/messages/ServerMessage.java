package com.sikiro.vehiclegateway.models.messages;

import com.sikiro.vehiclegateway.models.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServerMessage extends Message {

    private String content;
    private String deviceId;
    private Status command;
    private Integer frequency;

    public ServerMessage(Type type, String content) {
        super(type);
        this.content = content;
        this.deviceId = null;
        this.command = null;
        this.frequency = null;
    }

    public ServerMessage(Type type, String content, String deviceId) {
        super(type);
        this.content = content;
        this.deviceId = deviceId;
        this.command = null;
        this.frequency = null;
    }

    public ServerMessage(Type type, String content, Status command, String deviceId) {
        super(type);
        this.content = content;
        this.command = command;
        this.deviceId = deviceId;
        this.frequency = null;
    }

    public ServerMessage(Type type, String content, Integer frequency, String deviceId) {
        super(type);
        this.content = content;
        this.deviceId = deviceId;
        this.command = null;
        this.frequency = frequency;
    }
}
