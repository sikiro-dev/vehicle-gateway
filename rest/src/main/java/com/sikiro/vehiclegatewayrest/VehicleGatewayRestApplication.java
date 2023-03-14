package com.sikiro.vehiclegatewayrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class VehicleGatewayRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleGatewayRestApplication.class, args);
	}

}
