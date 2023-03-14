package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.messages.*;
import com.sikiro.vehiclegateway.models.vehicles.Event;
import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import com.sikiro.vehiclegatewaytcp.services.MessageService;
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
    private final MessageService messageService;
    private final PublisherService publisherService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("Received message");
        String stringMessage = (String) msg;

        if (stringMessage.isEmpty())
            return;

        ClientMessage clientMessage = messageService.readMessage(stringMessage);
        ServerMessage serverMessage = messageService.responseMessage(clientMessage);

        if (handleExceptions(ctx, clientMessage)) return;

        if (clientMessage instanceof HelloClientMessage helloClientMessage) {
            handleHello(ctx, helloClientMessage);
        } else if (clientMessage instanceof GoodbyeRequestClientMessage || clientMessage instanceof GoodbyeAckClientMessage) {
            handleGoodbye(ctx, clientMessage, serverMessage);
        } else if (clientMessage instanceof ReportClientMessage reportClientMessage) {
            handleReport(ctx, reportClientMessage);
        } else if (clientMessage instanceof CommandClientMessage commandClientMessage) {
            handleCommand(ctx, commandClientMessage);
        }

        Optional.ofNullable(serverMessage).ifPresent(m -> ctx.writeAndFlush(m.getContent() + "\r\n"));
    }

    private static boolean handleExceptions(ChannelHandlerContext ctx, ClientMessage clientMessage) {
        if (!(clientMessage instanceof HelloClientMessage || clientMessage instanceof GoodbyeAckClientMessage || clientMessage instanceof GoodbyeRequestClientMessage)
                && ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get() == null) {
            ctx.writeAndFlush("I DON'T KNOW YOU!\r\n");
            return true;
        }
        if (clientMessage instanceof HelloClientMessage && ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get() != null) {
            ctx.writeAndFlush("I ALREADY KNOW YOU!\r\n");
            return true;
        }
        return false;
    }

    private void handleHello(ChannelHandlerContext ctx, HelloClientMessage helloClientMessage) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(helloClientMessage.getDeviceId());
        vehicle.setLastEvents(List.of(Event.CONNECTED));
        ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).set(vehicle);
        channelRepository.put(vehicle.getId(), ctx.channel());
        publisherService.publish(vehicle);
    }

    private void handleGoodbye(ChannelHandlerContext ctx, ClientMessage clientMessage, ServerMessage serverMessage) {
        Optional.ofNullable(ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get()).ifPresent(
                vehicle -> {
                    vehicle.setLastEvents(List.of(Event.DISCONNECTED));
                    publisherService.publish(vehicle);
                });
        if (clientMessage instanceof GoodbyeRequestClientMessage && serverMessage != null)
            ctx.writeAndFlush(serverMessage.getContent() + "\r\n");
        channelInactive(ctx);
    }

    private void handleReport(ChannelHandlerContext ctx, ReportClientMessage reportClientMessage) {
        Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
        List<Event> lastEvents = new ArrayList<>();
        if (reportClientMessage.getStatus() != vehicle.getStatus()) {
            lastEvents.add(Event.STATUS_CHANGED);
        }
        if (!Objects.equals(reportClientMessage.getBatteryLevel(), vehicle.getBatteryLevel())) {
            lastEvents.add(Event.BATTERY_LEVEL_CHANGED);
        }
        if (!Objects.equals(reportClientMessage.getLatitude(), vehicle.getLatitude())
                || !Objects.equals(reportClientMessage.getLongitude(), vehicle.getLongitude())) {
            lastEvents.add(Event.LOCATION_CHANGED);
        }
        vehicle.setLatitude(reportClientMessage.getLatitude());
        vehicle.setLongitude(reportClientMessage.getLongitude());
        vehicle.setStatus(reportClientMessage.getStatus());
        vehicle.setBatteryLevel(reportClientMessage.getBatteryLevel());
        vehicle.setLastEvents(lastEvents);
        publisherService.publish(vehicle);
    }

    private void handleCommand(ChannelHandlerContext ctx, CommandClientMessage commandClientMessage) {
        Vehicle vehicle = ctx.channel().attr(VEHICLE_ATTRIBUTE_KEY).get();
        if (commandClientMessage.getResult().equals(CommandClientMessage.Result.SUCCESS)) {
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
