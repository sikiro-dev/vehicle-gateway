package vehiclegateway.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportClientMessage extends ClientMessage {

    private final Float latitude;

    private final Float longitude;

    private final Status status;

    private final Integer battery;

    ReportClientMessage(Float latitude, Float longitude, Status status, Integer battery) {
        super(Type.REPORT, Patterns.REPORT_CLIENT);
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.battery = battery;
    }

    public enum Status {
        RUNNING,
        RESTING
    }

}
