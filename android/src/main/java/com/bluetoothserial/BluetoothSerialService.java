package com.bluetoothserial;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BluetoothSerialService {
    private static final UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "BluetoothSerialService";

    private BluetoothAdapter adapter;
    private Map<String, BluetoothConnection> connections = new HashMap<>();

    public BluetoothSerialService(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean connect(BluetoothDevice device) {
        return connect(device, true);
    }

    public boolean connectInsecure(BluetoothDevice device) {
        return connect(device, false);
    }

    private boolean connect(BluetoothDevice device, boolean secure) {
        BluetoothConnection connectedThread = new BluetoothConnection(device, secure);
        connectedThread.start();

        connections.put(device.getAddress(), connectedThread);

        return true;
    }

    public boolean disconnectAllDevices() {
        boolean success = true;
        for(String address : connections.keySet()) {
            success = success & disconnect(address);
        }

        return success;
    }

    public boolean disconnect(BluetoothDevice device) {
        String address = device.getAddress();
        return disconnect(address);
    }

    public boolean disconnect(String address) {
        Log.d(TAG, "BEGIN disconnect device " + address);

        BluetoothConnection socket = getConnection(address);

        if(socket == null) {
            Log.e(TAG, "No connection found");
            return true;
        }

        if(!socket.isConnected()) {
            Log.i(TAG, "Device is already disconnected");
        } else {
            return socket.disconnect();
        }

        connections.remove(address);
        Log.d(TAG, "END disconnect device " + address);

        return true;
    }

    public boolean isConnected(String address) {
        Log.d(TAG, "BEGIN isConnected device " + address);

        BluetoothConnection socket = getConnection(address);

        if(socket == null) {
            Log.e(TAG, "No connection found");
            return false;
        }

        return socket.isConnected();
    }

    /**
     * Write to the connected Device via socket.
     *
     * @param address The device address to send
     * @param out  The bytes to write
     */
    public boolean write(String address, byte[] out) {
       // try {
           // Log.d(TAG, out.toString());

        /*    BluetoothSocket socket = getSocket(address);

            if(socket == null) {
                Log.e(TAG, "No connection found");
                return false;
            }

            Log.d(TAG, "" + socket.isConnected());
*/

        BluetoothConnection r;
            // Synchronize a copy of the ConnectedThread
            synchronized (this) {
                r = getConnection(address);
            }
            // Perform the write unsynchronized
            r.write(out);
            //socket.getOutputStream().write(buffer);
      //  } catch (IOException e) {
      //      Log.e(TAG, "Exception during write", e);
       //     return false;
        //}

        return true;
    }

    public byte[] read(String address) throws IOException {
        BluetoothConnection socket = getConnection(address);

        if(socket == null) {
            Log.e(TAG, "No connection found");
            return new byte[0];
        }

        if(!socket.isConnected()) {
            // TODO - throw exception
        }

        byte[] buffer = new byte[1024];

        //int bytes = socket.getInputStream().read(buffer);
        //byte[] rawdata = Arrays.copyOf(buffer, bytes);

        //return rawdata;

        return new byte[0];
    }

    private BluetoothConnection getConnection(String address) {
        return connections.get(address);
    }

    private class BluetoothConnection extends Thread {
        private BluetoothSocket socket = null;
        private final InputStream inStream;
        private final OutputStream outStream;

        public BluetoothConnection(BluetoothDevice device, boolean secure) {
            adapter.cancelDiscovery();

            createRfcomm(device, secure);

            inStream = getInputStream(socket);
            outStream = getOutputStream(socket);
        }

        private void createRfcomm(BluetoothDevice device, boolean secure) {
            String socketType = secure ? "Secure" : "Insecure";
            Log.d(TAG, "BEGIN create socket SocketType:" + socketType);

            try {
                if(secure) {
                    socket = device.createRfcommSocketToServiceRecord(DEFAULT_UUID);
                } else {
                    socket = device.createInsecureRfcommSocketToServiceRecord(DEFAULT_UUID);
                }

                Log.d(TAG, "END create socket SocketType:" + socketType);
                Log.d(TAG, "BEGIN connect SocketType:" + socketType);

                socket.connect();

                Log.i(TAG, "Connection success - SocketType:" + socketType);

                Log.d(TAG, "END connect SocketType:" + socketType);
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + socketType + "create() failed", e);
            }
        }

        private InputStream getInputStream(BluetoothSocket socket) {
            try {
                return socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Erro ao obter inputStream", e);
            }

            return null;
        }

        private OutputStream getOutputStream(BluetoothSocket socket) {
            try {
                return socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Erro ao obter outputStream", e);
            }

            return null;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
/*
                try {
                    // Read from the InputStream
                    bytes = inStream.read(buffer);
                    String data = new String(buffer, 0, bytes);
                    System.out.println(data);


                    // Send the new data String to the UI Activity
 //                   mHandler.obtainMessage(BluetoothSerial.MESSAGE_READ, data).sendToTarget();

                    // Send the raw bytestream to the UI Activity.
                    // We make a copy because the full array can have extra data at the end
                    // when / if we read less than its size.
                   /* if (bytes > 0) {
                        byte[] rawdata = Arrays.copyOf(buffer, bytes);
    //                    mHandler.obtainMessage(BluetoothSerial.MESSAGE_READ_RAW, rawdata).sendToTarget();
                    }


                    */
               /* } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
     //               connectionLost();
                    // Start the service over to restart listening mode
      //              BluetoothSerialService.this.start();
                    System.out.println(BluetoothSerialService.this);
                    break;
                }
*/

            }
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                outStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public boolean disconnect() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
                return false;
            }

            return true;
        }

        public boolean isConnected() {
            return socket.isConnected();
        }
    }
}