package com.sikiro.vehiclegateway.models.messages;

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

    private Side side;

    private Type type;

    private Vehicle data;

    private String content;

    public static Message fromServer(Type type, String content, String id) {
        Message message = new Message();
        Vehicle vehicle = new Vehicle();
        if (type == Type.COMMAND) {
            vehicle.setStatus(Status.fromString(content));
        }
        vehicle.setId(id);
        message.setSide(Side.SERVER);
        message.setType(type);
        message.setData(vehicle);
        message.setContent(content);
        return message;
    }

    private static Message of(Side side, Type type) {
        Message message = new Message();
        message.side = side;
        message.type = type;
        if (side.equals(Side.SERVER)) {
            message.content = type.getServer();
        } else {
            message.content = type.getClient();
        }
        return message;
    }

    public static Message response(Message message) {
        if (message.type == Type.GOODBYE_REQUEST) {
            return Message.of(Side.not(message.side), Type.GOODBYE_ACK);
        }
        if (Side.not(message.side) == message.type.responseSide) {
            return Message.of(Side.not(message.side), message.type);
        }
        return null;
    }

    public static Message fromClient(String content) {
        for (Type type : Type.values()) {
            if (content.matches(type.getClient())) {
                Message message = new Message();
                message.setSide(Side.CLIENT);
                message.setType(type);
                message.setData(parseData(content, type));
                message.setContent(content);
                return message;
            }
        }
        throw new IllegalArgumentException(UNKNOWN_MESSAGE);
    }

    private static Vehicle parseData(String body, Type type) {
        Vehicle vehicle = new Vehicle();
        Matcher matcher = Pattern.compile(type.getClient()).matcher(body);
        if (matcher.matches()) {
            switch (type) {
                case HELLO -> vehicle.setId(matcher.group(1));
                case REPORT -> {
                    vehicle.setLatitude(Double.parseDouble(matcher.group(1)));
                    vehicle.setLongitude(Double.parseDouble(matcher.group(3)));
                    vehicle.setStatus(Status.valueOf(matcher.group(5)));
                    vehicle.setBatteryLevel(Integer.valueOf(matcher.group(6)));
                }
            }
        }
        return vehicle;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Type {
        HELLO(HELLO_CLIENT, HELLO_SERVER, Side.SERVER),
        HEARTBEAT(HEARTBEAT_CLIENT, HEARTBEAT_SERVER, Side.SERVER),
        DATA(DATA_CLIENT, DATA_SERVER, Side.CLIENT),
        FREQUENCY(FREQUENCY_CLIENT, FREQUENCY_SERVER, Side.CLIENT),
        REPORT(REPORT_CLIENT, REPORT_SERVER, Side.SERVER),
        COMMAND(COMMAND_CLIENT, COMMAND_SERVER, Side.CLIENT),
        GOODBYE_REQUEST(Patterns.GOODBYE_REQUEST, Patterns.GOODBYE_REQUEST, Side.BOTH),
        GOODBYE_ACK(Patterns.GOODBYE_ACK, Patterns.GOODBYE_ACK, null);

        private final String client;
        private final String server;
        private final Side responseSide;

    }

    public enum Side {
        CLIENT,
        SERVER,
        BOTH;

        public static Side not(Side side) {
            if (side.equals(CLIENT))
                return SERVER;
            if (side.equals(SERVER))
                return CLIENT;
            return BOTH;
        }
    }

}

