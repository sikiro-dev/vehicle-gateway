package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;
import vehiclegateway.models.Status;

@Getter
@Setter
public class ReportClientMessage extends ClientMessage {

    private final Double latitude;

    private final Double longitude;

    private final Status status;

    private final Integer batteryLevel;

    public ReportClientMessage(Double latitude, Double longitude, Status status, Integer batteryLevel) {
        super(Type.REPORT);
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.batteryLevel = batteryLevel;
    }

}
