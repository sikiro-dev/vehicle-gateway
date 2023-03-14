package com.sikiro.vehiclegatewayrest.repository;

import com.sikiro.vehiclegateway.models.webhooks.Webhook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookRepository extends CrudRepository<Webhook, String> {

    List<Webhook> findAllByVehicleId(String vehicleId);

}
