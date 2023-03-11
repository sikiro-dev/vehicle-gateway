package com.sikiro.vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrequencyClientMessage extends ClientMessage {

    public FrequencyClientMessage() {
        super(Type.FREQUENCY);
    }

}
