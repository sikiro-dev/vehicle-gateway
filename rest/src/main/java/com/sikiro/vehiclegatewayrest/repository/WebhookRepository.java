package com.sikiro.vehiclegatewayrest.repository;

import com.sikiro.vehiclegateway.models.webhooks.Webhook;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@RedisHash
public interface WebhookRepository extends CrudRepository<Webhook, String> {
    
}
