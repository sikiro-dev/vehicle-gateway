package com.sikiro.vehiclegatewayrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sikiro")
public class VehicleGatewayRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleGatewayRestApplication.class, args);
	}

}
