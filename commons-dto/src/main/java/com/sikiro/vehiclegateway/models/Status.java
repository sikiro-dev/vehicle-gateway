package com.sikiro.vehiclegateway.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    RUNNING("RUN"),
    RESTING("REST");

    private final String command;
}
