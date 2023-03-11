package com.sikiro.vehiclegateway.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    RUNNING("RUN"),
    RESTING("REST");

    private final String command;

    public static Status fromString(String text) {
        for (Status b : Status.values()) {
            if (b.command.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
