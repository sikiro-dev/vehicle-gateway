package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HelloClientMessage extends ClientMessage {

    private final UUID deviceId;

    HelloClientMessage(UUID deviceId) {
        super(Type.HELLO, Patterns.HELLO_CLIENT);
        this.deviceId = deviceId;
    }

}
