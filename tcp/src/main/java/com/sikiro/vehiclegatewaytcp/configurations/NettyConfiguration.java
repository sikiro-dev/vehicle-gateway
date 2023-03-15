package com.sikiro.vehiclegatewaytcp.configurations;

import com.sikiro.vehiclegatewaytcp.server.ChannelRepository;
import com.sikiro.vehiclegatewaytcp.server.MessageChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(NettyProperties.class)
public class NettyConfiguration {

    private final NettyProperties nettyProperties;

    @Bean
    public ServerBootstrap serverBootstrap(MessageChannelInitializer messageChannelInitializer) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(messageChannelInitializer);
        bootstrap.option(ChannelOption.SO_BACKLOG, nettyProperties.getBacklog());
        return bootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(nettyProperties.getBossCount());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(nettyProperties.getWorkerCount());
    }

    @Bean
    public InetSocketAddress tcpSocketAddress() {
        return new InetSocketAddress(nettyProperties.getTcpPort());
    }

    @Bean
    public ChannelRepository channelRepository() {
        return new ChannelRepository();
    }
}
