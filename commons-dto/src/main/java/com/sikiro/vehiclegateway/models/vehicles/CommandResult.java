package com.sikiro.vehiclegateway.models.vehicles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandResult {

    SUCCESS("DONE!"),
    FAILURE("I CAN'T, SORRY.");

    private final String message;

    public static CommandResult fromString(String text) {
        for (CommandResult b : CommandResult.values()) {
            if (b.message.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with message " + text + " found");
    }


}
