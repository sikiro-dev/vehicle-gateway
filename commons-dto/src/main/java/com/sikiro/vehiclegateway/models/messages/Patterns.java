package com.sikiro.vehiclegateway.models.messages;


public class Patterns {

    private Patterns() {
        throw new IllegalStateException("Utility class");
    }

    private static final String COORDINATES = "-?\\d+(\\.\\d+)?";

    private static final String STATUS = "(RESTING|RUNNING)";

    private static final String BATTERY = "[1-9]|[1-9][0-9]|(100)";

    private static final String UUID = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    private static final String INFO = "I'M HERE ("
            + COORDINATES + ") (" + COORDINATES + "), "
            + STATUS + " AND CHARGED AT (" + BATTERY + ")%\\.";


    public static final String HELLO_CLIENT = "HELLO, I'M (" + UUID + ")!";

    public static final String HELLO_SERVER = "HI, NICE TO MEET YOU!";

    public static final String HEARTBEAT_CLIENT = "PING\\.";

    public static final String FREQUENCY_SERVER = "KEEP ME POSTED EVERY %d SECONDS.";

    public static final String FREQUENCY_CLIENT = "SURE, I WILL!";
    
    public static final String HEARTBEAT_SERVER = "PONG.";

    public static final String DATA_CLIENT = "FINE. " + INFO;

    public static final String DATA_SERVER = "HOW'S IT GOING?";

    public static final String REPORT_CLIENT = "REPORT. " + INFO;

    public static final String REPORT_SERVER = "OK, THANKS!";

    public static final String COMMAND_CLIENT = "(DONE!|I CAN'T, SORRY\\.)";

    public static final String COMMAND_SERVER = "HEY YOU, %s!";

    public static final String GOODBYE_REQUEST = "GOTTA GO!";
    
    public static final String GOODBYE_ACK = "SEE YA!";

    public static final String UNKNOWN = "I DON'T KNOW YOU!";

    public static final String ALREADY_KNOWN = "I ALREADY KNOW YOU!";

    public static final String UNEXPECTED_MESSAGE = "UNEXPECTED MESSAGE!";

    public static final String UNKNOWN_MESSAGE = "UNKNOWN MESSAGE!";

    public static final String MUST_GO = "I MUST GO, BYE!";

}
