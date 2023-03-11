package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportAckClientMessage extends ClientMessage {

    ReportAckClientMessage() {
        super(Type.REPORT_ACK, Patterns.REPORT_ACK_CLIENT);
    }

}
