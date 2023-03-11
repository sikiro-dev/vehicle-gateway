package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartbeatClientMessage extends ClientMessage {

    HeartbeatClientMessage() {
        super(Type.HEARTBEAT, Patterns.HEARTBEAT_CLIENT);
    }

}
