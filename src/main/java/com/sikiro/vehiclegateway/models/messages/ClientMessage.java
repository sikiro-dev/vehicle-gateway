package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public abstract class ClientMessage extends Message {

    private final Pattern pattern;

    public ClientMessage(Message.Type type, Pattern pattern) {
        super(type);
        this.pattern = pattern;
    }
}
