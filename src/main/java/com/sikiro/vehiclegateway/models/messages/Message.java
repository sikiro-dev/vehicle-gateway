package vehiclegateway.models.messages;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class Message {

    private final Type type;

    public enum Type {
        HELLO,
        HEARTBEAT,
        DATA,
        REPORT,
        REPORT_ACK,
        COMMAND,
        GOODBYE_REQUEST,
        GOODBYE_ACK,
        ERROR
    }

}

