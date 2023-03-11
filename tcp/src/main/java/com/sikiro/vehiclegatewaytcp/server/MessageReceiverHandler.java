package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegateway.models.messages.*;
import com.sikiro.vehiclegatewaytcp.services.MessageService;
import com.sikiro.vehiclegatewaytcp.services.VehicleService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.sikiro.vehiclegateway.models.Vehicle.VEHICLE_ATTRIBUTE_KEY;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class MessageReceiverHandler extends ChannelInboundHandlerAdapter {

    private final ChannelRepository channelRepository;
    private final MessageService messageService;
    private final VehicleService vehicleService;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String stringMessage = (String) msg;

        if (stringMessage.isEmpty())
            return;

        ClientMessage clientMessage = messageService.readMessage(stringMessage);

        if (clientMessage instanceof HelloClientMessage helloClientMessage) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(helloClientMessage.getDeviceId());
            ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).set(vehicle);
            channelRepository.put(vehicle.getId(), ctx.channel());
            vehicleService.save(vehicle);
        } else if (clientMessage instanceof GoodbyeRequestClientMessage || clientMessage instanceof GoodbyeAckClientMessage) {
            vehicleService.delete(ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get());
            if (clientMessage instanceof GoodbyeAckClientMessage)
                ctx.writeAndFlush(messageService.responseMessage(clientMessage).getContent() + "\r\n");
            channelInactive(ctx);
        } else if (clientMessage instanceof ReportClientMessage reportClientMessage) {
            Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
            vehicle.setLatitude(reportClientMessage.getLatitude());
            vehicle.setLongitude(reportClientMessage.getLongitude());
            vehicle.setStatus(reportClientMessage.getStatus());
            vehicle.setBatteryLevel(reportClientMessage.getBatteryLevel());
            vehicleService.save(vehicle);
        } else if (clientMessage instanceof CommandClientMessage commandClientMessage) {
            Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
            if (commandClientMessage.getResult().equals(CommandClientMessage.Result.SUCCESS))
                vehicle.setStatus(vehicle.getDesiredStatus());
            vehicleService.save(vehicle);
        }

        ctx.writeAndFlush(messageService.responseMessage(clientMessage).getContent() + "\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush(cause.getMessage() + "\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        //TODO: REMOVE LINKED VEHICLE (NOT ACTUALLY POSSIBLE SINCE SPRING DESTROYS BEANS)
        //Optional.ofNullable(channelRepository.get(ctx.channel())).ifPresent(vehicleService::delete);
        channelRepository.remove(ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get().getId());
        ctx.fireChannelInactive();
        ctx.close();
    }

    public void write(ServerMessage serverMessage, String vehicle) {
        Channel channel = channelRepository.get(vehicle);
        if (serverMessage.getType().equals(Message.Type.COMMAND))
            channel.attr(VEHICLE_ATTRIBUTE_KEY).get().setDesiredStatus(serverMessage.getCommand());
        if (channel.isActive())
            channel.writeAndFlush(serverMessage.getContent() + "\r\n");
    }

}
