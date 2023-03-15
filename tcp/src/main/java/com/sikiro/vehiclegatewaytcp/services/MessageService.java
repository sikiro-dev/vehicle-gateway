package com.sikiro.vehiclegatewaytcp.services;


import com.sikiro.vehiclegateway.models.messages.ClientMessage;
import com.sikiro.vehiclegateway.models.messages.ServerMessage;

public interface MessageService {

    ClientMessage readMessage(String message);

    ServerMessage responseMessage(ClientMessage clientMessage);

}
