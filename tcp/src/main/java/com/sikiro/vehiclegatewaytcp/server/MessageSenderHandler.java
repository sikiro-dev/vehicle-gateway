package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.messages.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.sikiro.vehiclegatewaytcp.server.ChannelRepository.MESSAGE_ATTRIBUTE_KEY;

@Component
@RequiredArgsConstructor
public class MessageSenderHandler extends ChannelInboundHandlerAdapter {

    private final ChannelRepository channelRepository;

    public void write(Message serverMessage) {
        Channel channel = channelRepository.get(serverMessage.getData().getId());
        channel.attr(MESSAGE_ATTRIBUTE_KEY).set(serverMessage);
        if (channel.isActive())
            channel.writeAndFlush(serverMessage.getContent() + "\r\n");
    }

}
