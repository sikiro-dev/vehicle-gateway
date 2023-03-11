package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodbyeRequestClientMessage extends ClientMessage {

    public GoodbyeRequestClientMessage() {
        super(Type.GOODBYE_REQUEST);
    }

}
