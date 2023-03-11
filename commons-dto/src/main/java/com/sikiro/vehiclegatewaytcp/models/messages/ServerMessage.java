package com.sikiro.vehiclegatewaytcp.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerMessage extends Message {

    private final String content;

    public ServerMessage(Type type, String content) {
        super(type);
        this.content = content;
    }
}
