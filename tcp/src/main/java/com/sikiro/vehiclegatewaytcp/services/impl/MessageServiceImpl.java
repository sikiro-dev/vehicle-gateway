package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.Status;
import com.sikiro.vehiclegateway.models.messages.*;
import com.sikiro.vehiclegatewaytcp.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;


@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public ClientMessage readMessage(String message) {
        Matcher matcher = Patterns.HELLO_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new HelloClientMessage(matcher.group(1));
        }
        matcher = Patterns.COMMAND_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new CommandClientMessage(CommandClientMessage.Result.fromString(matcher.group(1)));
        }
        matcher = Patterns.HEARTBEAT_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new HeartbeatClientMessage();
        }
        matcher = Patterns.FREQUENCY_CLIENT.matcher(message);
        if (matcher.matches()) {
            return new FrequencyClientMessage();
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
        throw new IllegalArgumentException("UNKNOWN MESSAGE: " + message);
    }

    @Override
    public ServerMessage responseMessage(ClientMessage clientMessage) {
        if (clientMessage instanceof HelloClientMessage) {
            return new ServerMessage(Message.Type.HELLO, Patterns.HELLO_SERVER);
        } else if (clientMessage instanceof HeartbeatClientMessage) {
            return new ServerMessage(Message.Type.HEARTBEAT, Patterns.HEARTBEAT_SERVER);
        } else if (clientMessage instanceof ReportClientMessage) {
            return new ServerMessage(Message.Type.REPORT, Patterns.REPORT_SERVER);
        } else if (clientMessage instanceof GoodbyeRequestClientMessage) {
            return new ServerMessage(Message.Type.GOODBYE_ACK, Patterns.GOODBYE_ACK_STRING);
        }
        return null;
    }

}
