package com.sikiro.vehiclegatewaytcp.server;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelRepository {
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

    public int size() {
        return channelCache.size();
    }

    public boolean containsKey(String key) {
        return channelCache.containsKey(key);
    }
}
