package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.messages.*;
import com.sikiro.vehiclegateway.models.vehicles.CommandResult;
import com.sikiro.vehiclegateway.models.vehicles.Event;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegatewaytcp.services.PublisherService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sikiro.vehiclegatewaytcp.server.ChannelRepository.VEHICLE_ATTRIBUTE_KEY;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
@Slf4j
public class MessageReceiverHandler extends ChannelInboundHandlerAdapter {

    private final ChannelRepository channelRepository;
    private final PublisherService publisherService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("Received message");
        String stringMessage = (String) msg;

        if (stringMessage.isEmpty())
            return;

        Message clientMessage = Message.fromClient(stringMessage);
        Message serverMessage = Message.response(clientMessage);

        if (handleExceptions(ctx, clientMessage)) return;

        if (clientMessage.getType().equals(Message.Type.HELLO)) {
            handleHello(ctx, clientMessage);
        } else if (clientMessage.getType().equals(Message.Type.GOODBYE_ACK) || clientMessage.getType().equals(Message.Type.GOODBYE_REQUEST)) {
            handleGoodbye(ctx, clientMessage, serverMessage);
        } else if (clientMessage.getType().equals(Message.Type.REPORT)) {
            handleReport(ctx, clientMessage);
        } else if (clientMessage.getType().equals(Message.Type.COMMAND)) {
            handleCommand(ctx, clientMessage);
        }

        Optional.ofNullable(serverMessage).ifPresent(m -> ctx.writeAndFlush(m.getContent() + "\r\n"));
    }

    private static boolean handleExceptions(ChannelHandlerContext ctx, Message clientMessage) {
        if (!(clientMessage.getType().equals(Message.Type.HELLO) ||
                clientMessage.getType().equals(Message.Type.GOODBYE_ACK) ||
                clientMessage.getType().equals(Message.Type.GOODBYE_REQUEST)) &&
                ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get() == null) {
            ctx.writeAndFlush("I DON'T KNOW YOU!\r\n");
            return true;
        }
        if (clientMessage.getType().equals(Message.Type.HELLO) && ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get() != null) {
            ctx.writeAndFlush("I ALREADY KNOW YOU!\r\n");
            return true;
        }
        return false;
    }

    private void handleHello(ChannelHandlerContext ctx, Message helloClientMessage) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(helloClientMessage.getData().getId());
        vehicle.setLastEvents(List.of(Event.CONNECTED));
        ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).set(vehicle);
        channelRepository.put(vehicle.getId(), ctx.channel());
        publisherService.publish(vehicle);
    }

    private void handleGoodbye(ChannelHandlerContext ctx, Message clientMessage, Message serverMessage) {
        Optional.ofNullable(ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get()).ifPresent(
                vehicle -> {
                    vehicle.setLastEvents(List.of(Event.DISCONNECTED));
                    publisherService.publish(vehicle);
                });
        if (clientMessage.getType().equals(Message.Type.GOODBYE_REQUEST)  && serverMessage != null)
            ctx.writeAndFlush(serverMessage.getContent() + "\r\n");
        channelInactive(ctx);
    }

    private void handleReport(ChannelHandlerContext ctx, Message reportClientMessage) {
        Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
        List<Event> lastEvents = new ArrayList<>();
        if (reportClientMessage.getData().getStatus() != vehicle.getStatus()) {
            lastEvents.add(Event.STATUS_CHANGED);
        }
        if (!Objects.equals(reportClientMessage.getData().getBatteryLevel(), vehicle.getBatteryLevel())) {
            lastEvents.add(Event.BATTERY_LEVEL_CHANGED);
        }
        if (!Objects.equals(reportClientMessage.getData().getLatitude(), vehicle.getLatitude())
                || !Objects.equals(reportClientMessage.getData().getLongitude(), vehicle.getLongitude())) {
            lastEvents.add(Event.LOCATION_CHANGED);
        }
        vehicle.setLatitude(reportClientMessage.getData().getLatitude());
        vehicle.setLongitude(reportClientMessage.getData().getLongitude());
        vehicle.setStatus(reportClientMessage.getData().getStatus());
        vehicle.setBatteryLevel(reportClientMessage.getData().getBatteryLevel());
        vehicle.setLastEvents(lastEvents);
        publisherService.publish(vehicle);
    }

    private void handleCommand(ChannelHandlerContext ctx, Message commandClientMessage) {
        Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
        if (commandClientMessage.getData().getLastCommandResult().equals(CommandResult.SUCCESS)) {
            vehicle.setStatus(vehicle.getDesiredStatus());
            vehicle.setLastEvents(List.of(Event.STATUS_CHANGED));
            publisherService.publish(vehicle);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush(cause.getMessage() + "\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Optional.ofNullable(ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get())
                .ifPresent(vehicle -> channelRepository.remove(vehicle.getId()));
        ctx.fireChannelInactive();
        ctx.close();
    }

}
