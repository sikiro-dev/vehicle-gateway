package vehiclegateway.services;

import vehiclegateway.models.messages.ClientMessage;

public interface MessageReader {

    ClientMessage readMessage(String message);

}
