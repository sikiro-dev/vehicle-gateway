package com.sikiro.vehiclegatewaytcp;

import com.sikiro.vehiclegatewaytcp.server.TCPServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class VehicleGatewayTcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleGatewayTcpApplication.class, args);
    }

    private final TCPServer tcpServer;

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
        return applicationReadyEvent -> tcpServer.start();
    }
}
