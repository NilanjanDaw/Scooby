package exhibition.scooby;

/**
 * Created by Nilanjan Daw on 19/12/2015.
 */
public class Constants {

    // Constants that indicate the current connection state
    public static final int STATE_BT_ON = 0;       // bluetooth is now ON
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int MESSAGE_SEND = 4;
    public static final int MESSAGE_RECEIVED = 5;
}
