package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegateway.models.messages.*;
import com.sikiro.vehiclegatewaytcp.services.MessageService;
import com.sikiro.vehiclegatewaytcp.services.PublisherService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.sikiro.vehiclegateway.models.Vehicle.VEHICLE_ATTRIBUTE_KEY;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class MessageReceiverHandler extends ChannelInboundHandlerAdapter {

    private final ChannelRepository channelRepository;
    private final MessageService messageService;
    private final PublisherService publisherService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String stringMessage = (String) msg;

        if (stringMessage.isEmpty())
            return;

        ClientMessage clientMessage = messageService.readMessage(stringMessage);
        ServerMessage serverMessage = messageService.responseMessage(clientMessage);

        if (!(clientMessage instanceof HelloClientMessage || clientMessage instanceof GoodbyeAckClientMessage || clientMessage instanceof GoodbyeRequestClientMessage)
                && ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get() == null) {
            ctx.writeAndFlush("I DON'T KNOW YOU!\r\n");
            return;
        }
        if (clientMessage instanceof HelloClientMessage && ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get() != null) {
            ctx.writeAndFlush("I ALREADY KNOW YOU!\r\n");
            return;
        }

        if (clientMessage instanceof HelloClientMessage helloClientMessage) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(helloClientMessage.getDeviceId());
            ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).set(vehicle);
            channelRepository.put(vehicle.getId(), ctx.channel());
            publisherService.publish(vehicle);
        } else if (clientMessage instanceof GoodbyeRequestClientMessage || clientMessage instanceof GoodbyeAckClientMessage) {
            if (clientMessage instanceof GoodbyeRequestClientMessage && serverMessage != null)
                ctx.writeAndFlush(serverMessage.getContent() + "\r\n");
            channelInactive(ctx);
        } else if (clientMessage instanceof ReportClientMessage reportClientMessage) {
            Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
            vehicle.setLatitude(reportClientMessage.getLatitude());
            vehicle.setLongitude(reportClientMessage.getLongitude());
            vehicle.setStatus(reportClientMessage.getStatus());
            vehicle.setBatteryLevel(reportClientMessage.getBatteryLevel());
            publisherService.publish(vehicle);
        } else if (clientMessage instanceof CommandClientMessage commandClientMessage) {
            Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
            if (commandClientMessage.getResult().equals(CommandClientMessage.Result.SUCCESS))
                vehicle.setStatus(vehicle.getDesiredStatus());
            publisherService.publish(vehicle);
        }

        Optional.ofNullable(serverMessage).ifPresent(m -> ctx.writeAndFlush(m.getContent() + "\r\n"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush(cause.getMessage() + "\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        //TODO: REMOVE LINKED VEHICLE (NOT ACTUALLY POSSIBLE SINCE SPRING DESTROYS BEANS)
        //Optional.ofNullable(channelRepository.get(ctx.channel())).ifPresent(vehicleService::delete);
        Optional.ofNullable(ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get())
                .ifPresent(vehicle -> channelRepository.remove(vehicle.getId()));
        ctx.fireChannelInactive();
        ctx.close();
    }

}
