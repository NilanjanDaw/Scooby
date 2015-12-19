package exhibition.scooby;


import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Nilanjan Daw on 19/12/2015.
 */

public class DataTransferThread implements Runnable {

    private BluetoothSocket socket;
    private Handler handler;
    private InputStream inputStream;
    private OutputStream outputStream;

    DataTransferThread(BluetoothSocket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
        if (socket != null) {
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int numberOfBytes = -1;
        while (true) {
            try {
                if (inputStream != null) {
                    numberOfBytes = inputStream.read(buffer);
                    handler.obtainMessage(Constants.MESSAGE_RECEIVED, numberOfBytes, -1, buffer).sendToTarget();
                } else {
                    Log.d("DataTransferThread", "inputStream null");
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void write(String send) {
        byte[] sendBytes = send.getBytes();
        try {
            if (outputStream != null) {
                outputStream.write(sendBytes);
            } else {
                Log.d("DataTransferThread", "outputStream null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            if (inputStream != null)
                inputStream.close();
            if (outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
