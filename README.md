# VEHICLE GATEWAY

The service is composed of two parts: 
* A `REST API` that allows to manage the vehicles and the webhooks
* A `web socket` server that allows to receive the vehicle messages

Those two parts talk to each other through a Redis server.\

## REDIS

There are two  pub/sub channels:
* `vehicles` this channel is used by the web socket server to send the vehicle messages to the REST API 
(the REST API use this channel to respond getVehicle requests)
* `messages` this channel is used by the REST API to send the endpoint messages to the web socket server

There is also a stream:
* `vehicle` this stream is updated by the web socket and used by the REST API to retrieve 
the vehicle update and decide if it has to send a webhook

## WEB SOCKET
the web socket server is a simple server that receives the vehicle messages and send them to redis.
it uses a String based protocol to comunicate with the vehicles.
The server is available at `localhost:8090`

## REST API
Next there is a list of the available endpoints:

### GET /api/vehicles/{id}
This endpoint returns the vehicle with the given id, if connected, otherwise it returns a 404 error.\
`curl --location --request GET 'http://localhost:8080/api/vehicle/123e4567-e89b-12d3-a456-426655440000'`

### GET /api/vehicles/{id}/(RUN|REST)
This endpoint allows to send a command to the vehicle with the given id.\
`curl --location --request GET 'http://localhost:8080/api/vehicle/123e4567-e89b-12d3-a456-426655440000/RUN'`

### GET /api/vehicles/{id}/disconnect
This endpoint allows to disconnect the vehicle with the given id.\
`curl --location --request GET 'http://localhost:8080/api/vehicle/123e4567-e89b-12d3-a456-426655440000/disconnect'`

### POST /api/webhook
This endpoint allows to create a webhook, it requires a json body with the following 
structure (it will execute a POST request to the given url):
```json
{
  "url": "https://postman-echo.com/post",
  "vehicleId": "123e4567-e89b-12d3-a456-426655440000"
}
```
`curl --location --request POST 'http://localhost:8080/api/webhook' \
--header 'Content-Type: application/json' \
--data '{
"url": "https://postman-echo.com/post",
"vehicleId": "123e4567-e89b-12d3-a456-426655440000"
}'`

### GET /api/webhook
This endpoint returns the list of the webhooks.\
`curl --location --request GET 'http://localhost:8080/api/webhook'`

### DELETE /api/webhooks/{id}
This endpoint allows to delete a webhook.\
`curl --location --request DELETE 'http://localhost:8080/api/webhook/123e4567-e89b-12d3-a456-426655440000'`

## DOCKER

To run the service with docker you can use the following command:\
`bash ./deploy.sh`\
It will build the docker image and run the service using docker swarm (so you need to have docker swarm initialized).\
By default, there will be 2 replicas for each service (REST and web socket), you can augment this number with docker service scale command.\
Otherwise you can run the service with the docker-compose.yml file.