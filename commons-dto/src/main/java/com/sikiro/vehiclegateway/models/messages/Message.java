package com.sikiro.vehiclegateway.models.messages;

import com.sikiro.vehiclegateway.models.vehicles.CommandResult;
import com.sikiro.vehiclegateway.models.vehicles.Status;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sikiro.vehiclegateway.models.messages.Patterns.*;

@Setter
@Getter
@EqualsAndHashCode
public class Message {

    private Type type;

    private Vehicle data;

    private String content;

    public static Message fromServer(Type type, String content, String id) {
        Message message = new Message();
        Vehicle vehicle = new Vehicle();
        if (type == Type.COMMAND) {
            vehicle.setDesiredStatus(Status.fromString(content));
        }
        vehicle.setId(id);
        message.setType(type);
        message.setData(vehicle);
        message.setContent(content);
        return message;
    }

    public static Message response(Message message) {
        if (message.type.canResponse) {
            return fromServer(message.type, message.type.server, message.data.getId());
        }
        return null;
    }

    public static Message fromClient(String content) {
        for (Type type : Type.values()) {
            if (content.matches(type.getClient())) {
                Message message = new Message();
                message.setType(type);
                message.setData(parseData(content, type));
                message.setContent(content);
                return message;
            }
        }
        throw new IllegalArgumentException("Unknown message type");
    }

    private static Vehicle parseData(String body, Type type) {
        Vehicle vehicle = new Vehicle();
        Matcher matcher = Pattern.compile(type.getClient()).matcher(body);
        if (matcher.matches()) {
            switch (type) {
                case HELLO -> vehicle.setId(matcher.group(1));
                case REPORT -> {
                    vehicle.setLatitude(Double.parseDouble(matcher.group(2)));
                    vehicle.setLongitude(Double.parseDouble(matcher.group(4)));
                    vehicle.setStatus(Status.valueOf(matcher.group(6)));
                    vehicle.setBatteryLevel(Integer.valueOf(matcher.group(7)));
                }
                case COMMAND -> vehicle.setLastCommandResult(CommandResult.fromString(matcher.group(1)));
            }
        }
        return vehicle;
    }


    @RequiredArgsConstructor
    @Getter
    public enum Type {
        HELLO(HELLO_CLIENT, HELLO_SERVER, true),
        HEARTBEAT(HEARTBEAT_CLIENT, HEARTBEAT_SERVER, true),
        DATA(null, DATA_SERVER, false),
        FREQUENCY(FREQUENCY_CLIENT, FREQUENCY_SERVER, false),
        REPORT(REPORT_CLIENT, REPORT_SERVER, true),
        COMMAND(COMMAND_CLIENT, COMMAND_SERVER, false),
        GOODBYE_REQUEST(Patterns.GOODBYE_REQUEST, Patterns.GOODBYE_REQUEST, true),
        GOODBYE_ACK(Patterns.GOODBYE_ACK, Patterns.GOODBYE_ACK, false);

        private final String client;
        private final String server;
        private final boolean canResponse;

    }

}

