package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandClientMessage extends ClientMessage {

    private final Result result;

    CommandClientMessage(Result result) {
        super(Type.COMMAND, Patterns.COMMAND_CLIENT);
        this.result = result;
    }

    public enum Result {
        SUCCESS("DONE!"),
        FAILURE("I CAN'T, SORRY.");

        private final String message;

        Result(String message) {
            this.message = message;
        }

        public static Result fromString(String text) {
            for (Result b : Result.values()) {
                if (b.message.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("No constant with message " + text + " found");
        }
    }
}
