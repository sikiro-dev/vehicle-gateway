package com.sikiro.vehiclegatewaytcp.server;

import com.sikiro.vehiclegateway.models.vehicles.Vehicle;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelRepository {

    public static final AttributeKey<Vehicle> VEHICLE_ATTRIBUTE_KEY = AttributeKey.newInstance("VEHICLE");

    private final ConcurrentMap<String, Channel> channelCache = new ConcurrentHashMap<>();

    public void put(String key, Channel value) {
        channelCache.put(key, value);
    }

    public Channel get(String key) {
        return channelCache.get(key);
    }

    public void remove(String key) {
        channelCache.remove(key);
    }

    public boolean containsKey(String key) {
        return channelCache.containsKey(key);
    }

}
