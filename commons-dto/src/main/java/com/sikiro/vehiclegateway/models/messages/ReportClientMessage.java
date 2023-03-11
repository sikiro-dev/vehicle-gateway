package com.sikiro.vehiclegateway.models.messages;

import com.sikiro.vehiclegateway.models.Status;
import lombok.Getter;
import lombok.Setter;

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
