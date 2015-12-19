package exhibition.scooby;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends Activity {

    public static final int REQUEST_ENABLE_BT = 20;
    public static final int REQUEST_DISCOVERABLE_BT = 10;
    public static final String TAG = "BluetoothActivity";
    private BluetoothAdapter adapter;
    private BluetoothDevice mDevice;
    private BluetoothSocket socket;
    private String target;
    EditText sendData;
    Button send;
    EditText deviceName;
    Button connect;
    public static int mState;
    public static Handler handler;
    public ConnectThread connector;
    public DataTransferThread dataTransfer;
    public BluetoothActivity() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case Constants.STATE_CONNECTED:
                        showText(message.obj.toString());
                        if (socket != null) {
                            socket = connector.getSocket();
                            startDataTransfer();
                        }
                        else {
                            Log.d(TAG, "Socket null(Handler)");
                        }
                        break;
                    case Constants.MESSAGE_RECEIVED:
                        byte[] bufferMessage = (byte[]) message.obj;
                        String msg = new String(bufferMessage,0, message.arg1);
                        sendData.setText(msg);
                        break;
                }

            }
        };
    }

    public static final UUID UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        sendData = (EditText) findViewById(R.id.editSend);
        deviceName = (EditText) findViewById(R.id.btaddress);
        connect = (Button) findViewById(R.id.connect);
        send = (Button) findViewById(R.id.buttonSend);
        adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null) {
            showText("No Bluetooth Device Found");
        }
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                target = deviceName.getText().toString();
                if(!adapter.isEnabled()) {
                    Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);

                }
                else {
                    getList();
                    connector = new ConnectThread(mDevice, adapter);
                    new Thread(connector).start();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send = sendData.getText().toString();
                Log.d(TAG, "Sending: " + send);
                dataTransfer.write(send);
                sendData.setText("");
            }
        });
    }

    private void showText(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (socket != null) {
            try {
                dataTransfer.cancel();
                connector.cancel();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (adapter.isEnabled())
            adapter.disable();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            showText("Bluetooth ON");
            mState = Constants.STATE_BT_ON;
            startConnection();

        }
    }

    private void startConnection() {
        if (mState == Constants.STATE_BT_ON) {
            getList();
            connector = new ConnectThread(mDevice, adapter);
            new Thread(connector).start();
        }
    }

    private void startDataTransfer() {

        Log.d("Hello:", connector.getSocket().toString());
        dataTransfer = new DataTransferThread(socket, handler);
        new Thread(dataTransfer).start();
    }


    public void getList() {
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            Log.d(TAG, device.getName());
            if (device.getName().equalsIgnoreCase(target)) {
                mDevice = device;
            }
        }
    }
}
