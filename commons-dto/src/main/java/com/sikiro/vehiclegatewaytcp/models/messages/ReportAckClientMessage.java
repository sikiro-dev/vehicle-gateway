package com.sikiro.vehiclegatewaytcp.models.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportAckClientMessage extends ClientMessage {

    public ReportAckClientMessage() {
        super(Type.REPORT_ACK);
    }

}
