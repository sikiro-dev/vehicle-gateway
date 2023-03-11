package com.sikiro.vehiclegatewaytcp.models.messages;

import java.util.regex.Pattern;

public class Patterns {

    private Patterns() {
        throw new IllegalStateException("Utility class");
    }

    public static final Pattern COORDINATES = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static final Pattern STATUS = Pattern.compile("(RESTING|RUNNING)");

    public static final Pattern BATTERY = Pattern.compile("[1-9]|[1-9][0-9]|(100)");

    public static final Pattern UUID = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

    public static final Pattern HELLO_CLIENT = Pattern.compile("^HELLO, I'M (" + UUID + ")!$");

    public static final String HELLO_SERVER = "HI, NICE TO MEET YOU!";

    public static final Pattern HEARTBEAT_CLIENT = Pattern.compile("^PING\\.$");

    public static final String HEARTBEAT_SERVER = "PONG\\.";

    public static final String DATA_SERVER = "HOW'S IT GOING?";

    public static final Pattern REPORT_ACK_CLIENT = Pattern.compile("^SURE, I WILL!$");

    public static final String REPORT_REQUEST_SERVER = "KEEP ME POSTED EVERY %d SECONDS\\.";

    public static final Pattern REPORT_CLIENT = Pattern.compile("^(FINE|REPORT). I'M HERE ("
            + COORDINATES + ") (" + COORDINATES + "), "
            + STATUS + " AND CHARGED AT (" + BATTERY + ")%\\.$");

    public static final String REPORT_SERVER = "OK, THANKS!";

    public static final Pattern COMMAND_CLIENT = Pattern.compile("^(DONE!|I CAN'T, SORRY\\.)$");

    public static final String COMMAND_SERVER = "HEY YOU, %s!";

    public static final Pattern GOODBYE_REQUEST = Pattern.compile("^GOTTA GO!$");

    public static final Pattern GOODBYE_ACK = Pattern.compile("^SEE YA!$");


}
