package com.sikiro.vehiclegateway.models.messages;


public class Patterns {

    private Patterns() {
        throw new IllegalStateException("Utility class");
    }

    public static final String COORDINATES = "-?\\d+(\\.\\d+)?";

    public static final String STATUS = "(RESTING|RUNNING)";

    public static final String BATTERY = "[1-9]|[1-9][0-9]|(100)";

    public static final String UUID = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    public static final String HELLO_CLIENT = "HELLO, I'M (" + UUID + ")!";

    public static final String HELLO_SERVER = "HI, NICE TO MEET YOU!";

    public static final String HEARTBEAT_CLIENT = "PING\\.";

    public static final String FREQUENCY_SERVER = "KEEP ME POSTED EVERY %d SECONDS.";

    public static final String FREQUENCY_CLIENT = "SURE, I WILL!";
    
    public static final String HEARTBEAT_SERVER = "PONG.";

    public static final String DATA_SERVER = "HOW'S IT GOING?";

    public static final String REPORT_CLIENT = "(FINE|REPORT). I'M HERE ("
            + COORDINATES + ") (" + COORDINATES + "), "
            + STATUS + " AND CHARGED AT (" + BATTERY + ")%\\.";

    public static final String REPORT_SERVER = "OK, THANKS!";

    public static final String COMMAND_CLIENT = "(DONE!|I CAN'T, SORRY\\.)";

    public static final String COMMAND_SERVER = "HEY YOU, %s!";

    public static final String GOODBYE_REQUEST = "GOTTA GO!";
    
    public static final String GOODBYE_ACK = "SEE YA!";

    private static final String UNKNOWN = "I DON'T KNOW YOU!";

    private static final String ALREADY_KNOWN = "I ALREADY KNOW YOU!";



}
