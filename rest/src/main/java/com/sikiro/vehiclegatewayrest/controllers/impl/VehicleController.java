package com.sikiro.vehiclegatewayrest.controllers.impl;

import com.sikiro.vehiclegateway.models.Status;
import com.sikiro.vehiclegateway.models.Vehicle;
import com.sikiro.vehiclegatewayrest.controllers.Controller;
import com.sikiro.vehiclegatewayrest.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController extends Controller {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Vehicle>> getVehicle(@PathVariable String id) {
        vehicleService.getUpdate(id);
        return vehicleService.getVehicle(id)
                .map(ResponseEntity::ok)
                .timeout(Duration.ofMillis(3000), Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("{id}/frequency/{frequency}")
    public ResponseEntity<Void> sendUpdateFrequency(@PathVariable String id, @PathVariable int frequency) {
        vehicleService.sendUpdateFrequency(id, frequency);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/command/{command}")
    public ResponseEntity<Void> sendCommand(@PathVariable String id, @PathVariable String command) {
        vehicleService.sendCommand(id, Status.fromString(command));
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/disconnect")
    public ResponseEntity<Void> disconnect(@PathVariable String id) {
        vehicleService.disconnect(id);
        return ResponseEntity.ok().build();
    }

}
