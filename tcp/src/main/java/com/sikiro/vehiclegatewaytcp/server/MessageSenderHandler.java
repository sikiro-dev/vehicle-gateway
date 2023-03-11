package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.messages.Message;
import com.sikiro.vehiclegateway.models.messages.ServerMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.sikiro.vehiclegateway.models.Vehicle.VEHICLE_ATTRIBUTE_KEY;

@Component
@RequiredArgsConstructor
public class MessageSenderHandler extends ChannelInboundHandlerAdapter {

    private final ChannelRepository channelRepository;

    public void write(ServerMessage serverMessage) {
        Channel channel = channelRepository.get(serverMessage.getDeviceId());
        if (serverMessage.getType().equals(Message.Type.COMMAND))
            channel.attr(VEHICLE_ATTRIBUTE_KEY).get().setDesiredStatus(serverMessage.getCommand());
        if (channel.isActive())
            channel.writeAndFlush(serverMessage.getContent() + "\r\n");
    }

}
