package exhibition.scooby;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Created by Nilanjan Daw on 18/12/2015.
 */
public class ConnectThread implements Runnable {

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private BluetoothAdapter adapter;


    ConnectThread(BluetoothDevice device, BluetoothAdapter adapter) {
        this.device = device;
        this.adapter = adapter;
    }
    @Override
    public void run() {
        /**
        adapter.cancelDiscovery();
        if (socket != null) {
            try {
                BluetoothActivity.mState = Constants.STATE_CONNECTING;
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         **/
        if (adapter.isDiscovering())
            adapter.cancelDiscovery();
        BluetoothConnector bluetoothConnector = new BluetoothConnector(device, true, adapter, null);
        BluetoothConnector.BluetoothSocketWrapper socketWrapper = null;
        try {
            socketWrapper = bluetoothConnector.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socketWrapper != null) {
            this.socket = socketWrapper.getUnderlyingSocket();
        }
        BluetoothActivity.mState = Constants.STATE_CONNECTED;
        Message message = BluetoothActivity.handler.obtainMessage();
        message.obj = "Connected to Device";
        message.what = Constants.STATE_CONNECTED;
        BluetoothActivity.handler.sendMessage(message);
    }
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
            Log.d(BluetoothActivity.TAG, e.toString());
        }
    }

    public BluetoothSocket getSocket() {
        return socket;
    }
}
