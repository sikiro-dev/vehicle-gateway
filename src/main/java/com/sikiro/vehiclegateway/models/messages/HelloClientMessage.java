package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HelloClientMessage extends ClientMessage {

    private final UUID deviceId;

    public HelloClientMessage(UUID deviceId) {
        super(Type.HELLO);
        this.deviceId = deviceId;
    }

}
