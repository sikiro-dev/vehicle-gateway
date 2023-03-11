package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodbyeClientMessage extends ClientMessage {

    GoodbyeClientMessage() {
        super(Type.GOODBYE, Patterns.GOODBYE_CLIENT);
    }

}
