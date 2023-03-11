package com.sikiro.vehiclegatewaytcp.services.impl;

import com.sikiro.vehiclegateway.models.Status;
import com.sikiro.vehiclegateway.models.messages.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageReaderImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void helloMessage() {
        String input = "HELLO, I'M 123e4567-e89b-12d3-a456-426655440000!";
        ClientMessage message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.HELLO, message.getType());
        if (message instanceof HelloClientMessage helloMessage) {
            Assertions.assertEquals("123e4567-e89b-12d3-a456-426655440000", helloMessage.getDeviceId());
        } else {
            Assertions.fail("Wrong message type");
        }
    }

    @Test
    void heartbeatMessage() {
        String input = "PING.";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.HEARTBEAT, message.getType());
        Assertions.assertEquals(HeartbeatClientMessage.class, message.getClass());
    }

    @Test
    void reportMessage() {
        String input = "FINE. I'M HERE 45.021561650 8.156484, RESTING AND CHARGED AT 42%.";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.REPORT, message.getType());
        if (message instanceof ReportClientMessage reportMessage) {
            Assertions.assertEquals(45.021561650, reportMessage.getLatitude());
            Assertions.assertEquals(8.156484, reportMessage.getLongitude());
            Assertions.assertEquals(Status.RESTING, reportMessage.getStatus());
            Assertions.assertEquals(42, reportMessage.getBatteryLevel());
        } else {
            Assertions.fail("Wrong message type");
        }
    }

    @Test
    void reportAckMessage() {
        String input = "SURE, I WILL!";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.REPORT_ACK, message.getType());
        Assertions.assertEquals(ReportAckClientMessage.class, message.getClass());
    }

    @Test
    void commandMessageSuccess() {
        String input = "DONE!";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.COMMAND, message.getType());
        if (message instanceof CommandClientMessage commandMessage) {
            Assertions.assertEquals(CommandClientMessage.Result.SUCCESS, commandMessage.getResult());
        } else {
            Assertions.fail("Wrong message type");
        }
    }

    @Test
    void commandMessageFailure() {
        String input = "I CAN'T, SORRY.";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.COMMAND, message.getType());
        if (message instanceof CommandClientMessage commandMessage) {
            Assertions.assertEquals(CommandClientMessage.Result.FAILURE, commandMessage.getResult());
        } else {
            Assertions.fail("Wrong message type");
        }
    }

    @Test
    void goodbyeRequestMessage() {
        String input = "GOTTA GO!";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.GOODBYE_REQUEST, message.getType());
        Assertions.assertEquals(GoodbyeRequestClientMessage.class, message.getClass());
    }

    @Test
    void goodbyeAckMessage() {
        String input = "SEE YA!";
        Message message = messageService.readMessage(input);
        Assertions.assertEquals(Message.Type.GOODBYE_ACK, message.getType());
        Assertions.assertEquals(GoodbyeAckClientMessage.class, message.getClass());
    }

    @Test
    void unknownMessage() {
        String input = "UNKNOWN";
        Assertions.assertThrows(IllegalArgumentException.class, () -> messageService.readMessage(input));
    }
}