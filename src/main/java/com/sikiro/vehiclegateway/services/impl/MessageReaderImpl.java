package vehiclegateway.services.impl;

import org.springframework.stereotype.Service;
import vehiclegateway.models.Status;
import vehiclegateway.models.messages.*;
import vehiclegateway.services.MessageReader;

import java.util.UUID;
import java.util.regex.Matcher;

@Service
public class MessageReaderImpl implements MessageReader {

    @Override
    public ClientMessage readMessage(String message) {
        Matcher matcher = Patterns.HELLO_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new HelloClientMessage(UUID.fromString(matcher.group(1)));
        }
        matcher = Patterns.COMMAND_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new CommandClientMessage(CommandClientMessage.Result.fromString(matcher.group(1)));
        }
        matcher = Patterns.HEARTBEAT_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new HeartbeatClientMessage();
        }
        matcher = Patterns.REPORT_ACK_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new ReportAckClientMessage();
        }
        matcher = Patterns.REPORT_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new ReportClientMessage(
                    Double.parseDouble(matcher.group(2)),
                    Double.parseDouble(matcher.group(4)),
                    Status.valueOf(matcher.group(6)),
                    Integer.valueOf(matcher.group(7))
            );
        }
        matcher = Patterns.GOODBYE_REQUEST.matcher(message);
        if (matcher.matches()) {
            return new GoodbyeRequestClientMessage();
        }
        matcher = Patterns.GOODBYE_ACK.matcher(message);
        if (matcher.matches()) {
            return new GoodbyeAckClientMessage();
        }
        throw new IllegalArgumentException("Unknown message: " + message);
    }
}
