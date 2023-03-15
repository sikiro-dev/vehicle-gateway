package com.sikiro.vehiclegateway.models.messages;

import com.sikiro.vehiclegateway.models.vehicles.CommandResult;
import com.sikiro.vehiclegateway.models.vehicles.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void helloMessage() {
        String input = "HELLO, I'M 123e4567-e89b-12d3-a456-426655440000!";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.HELLO, message.getType());
        Assertions.assertEquals("123e4567-e89b-12d3-a456-426655440000", message.getData().getId());

    }

    @Test
    void heartbeatMessage() {
        String input = "PING.";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.HEARTBEAT, message.getType());
    }

    @Test
    void reportMessage() {
        String input = "FINE. I'M HERE 45.021561650 8.156484, RESTING AND CHARGED AT 42%.";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.REPORT, message.getType());
        Assertions.assertEquals(45.021561650, message.getData().getLatitude());
        Assertions.assertEquals(8.156484, message.getData().getLongitude());
        Assertions.assertEquals(Status.RESTING, message.getData().getStatus());
        Assertions.assertEquals(42, message.getData().getBatteryLevel());

    }

    @Test
    void reportAckMessage() {
        String input = "SURE, I WILL!";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.FREQUENCY, message.getType());
    }

    @Test
    void commandMessageSuccess() {
        String input = "DONE!";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.COMMAND, message.getType());
        Assertions.assertEquals(CommandResult.SUCCESS, message.getData().getLastCommandResult());

    }

    @Test
    void commandMessageFailure() {
        String input = "I CAN'T, SORRY.";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.COMMAND, message.getType());
        Assertions.assertEquals(CommandResult.FAILURE, message.getData().getLastCommandResult());
    }

    @Test
    void goodbyeRequestMessage() {
        String input = "GOTTA GO!";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.GOODBYE_REQUEST, message.getType());
    }

    @Test
    void goodbyeAckMessage() {
        String input = "SEE YA!";
        Message message = Message.fromClient(input);
        Assertions.assertEquals(Message.Type.GOODBYE_ACK, message.getType());
    }

    @Test
    void unknownMessage() {
        String input = "UNKNOWN";
        Assertions.assertThrows(IllegalArgumentException.class, () -> Message.fromClient(input));
    }
}