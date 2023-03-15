#!/bin/bash

docker compose build

docker stack deploy -c redis.yml vehicle_gateway

while [ -z "$(docker ps -f name=vehicle_gateway_redis --format "{{.ID}}")" ]; do
    printf "Waiting for redis to start...\r"
    sleep 1
done

printf "Redis started!                 \n"

REDIS_ID=$(docker ps -f name=vehicle_gateway_redis --format "{{.ID}}")

docker exec -it "$REDIS_ID" redis-cli XGROUP CREATE vehicles vehicles $ mkstream

docker stack deploy -c service.yml vehicle_gateway
