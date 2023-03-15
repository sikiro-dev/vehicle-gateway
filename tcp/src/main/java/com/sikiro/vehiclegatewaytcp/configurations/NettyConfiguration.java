package com.sikiro.vehiclegatewaytcp.configurations;

import com.sikiro.vehiclegatewaytcp.server.ChannelRepository;
import com.sikiro.vehiclegatewaytcp.server.MessageChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@RequiredArgsConstructor
public class NettyConfiguration {

    @Value("${tcp.port:8090}")
    private Integer port;

    @Bean
    public ServerBootstrap serverBootstrap(MessageChannelInitializer messageChannelInitializer) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(messageChannelInitializer);
        return bootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public InetSocketAddress tcpSocketAddress() {
        return new InetSocketAddress(port);
    }

    @Bean
    public ChannelRepository channelRepository() {
        return new ChannelRepository();
    }
}
