# Capacitor Bluetooth Serial Plugin

A client implementation for interacting with Bluetooth

Supported platforms

- [x] Android
- [ ] iOS

## Usage

Install the plugin via npm
```
npm install --save capacitor-bluetooth-serial
```

In your capacitor project, make sure to register the Android plugin in
in the projects `MainActivity` as follows

```java
import com.bluetoothserial.plugin.BluetoothLEClient;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      add(BluetoothSerial.class);
    }});
  }
}
```



```typescript
import {Plugins} from "@capacitor/core";

const { BluetoothSerial } = Plugins;

//...do something with plugin

```

## API Documentation

Interface and type definitions can be found [here](./src/definitions.ts).

# API

## Methods

- [BluetoothSerial.isEnabled](#isenabled)
- [BluetoothSerial.scan](#scan)
- [BluetoothSerial.connect](#connect)
- [BluetoothSerial.disconnect](#disconnect)


## isEnabled

Reports if bluetooth is enabled.

  `isEnabled(): Promise<BluetoothEnabledResult>;`

### Description

Function `isEnabled` calls the success whatever bluetooth is enabled or not. The promise will contain an attribute `enabled` indicating if bluetooth is enabled or *not* enabled. The failure callback will be called only if an error occurs (e.g. app does not have permission to access bluetooth).

### Parameters

None.

### Quick Example

```typescript
BluetoothSerial
  .isEnabled()
  .then((response: BluetoothEnabledResult) => {
    const message = response.enabled ? 'enabled' : 'disabled';
    console.log(`Bluetooth is ${message}`);
  })
  .catch(() => {
    console.log('Error checking bluetooth status');
  });
```

## scan

Discover devices visible and close to the device

  `scan(): Promise<BluetoothScanResult>;`

### Description

#### Android

Function `scan` discovers Bluetooth devices close to the device and visible. The success callback is called with a list of objects similar to `list`, or an empty list if no devices are found.

Example list passed to success callback.

```json
[{
    "class": 0,
    "id": "00:11:22:33:44:55",
    "address": "00:11:22:33:44:55",
    "name": "Device 1"
}, {
    "class": 7936,
    "id": "01:23:6645:4D67:89:00",
    "address": "01:23:6645:4D67:89:00",
    "name": "Device 2"
}]
```

The discovery process takes a while to happen.
You may want to show a progress indicator while waiting for the discover proces to finish, and the sucess callback to be invoked.

Calling `connect` on an unpaired Bluetooth device should begin the Android pairing process.

### Parameters

None.

### Quick Example

```typescript
BluetoothSerial
  .scan()
  .then((result: BluetoothScanResult) => {
    result.devices.forEach((device: BluetoothDevice) {
        console.log(device.id);
    });
  })
  .catch(() => {
    console.log('Error scanning devices');
  });
```

## connect

Connect to a Bluetooth device.

  `connect(options: BluetoothConnectOptions): Promise<void>`;

### Description

Function `connect` connects to a Bluetooth device.  The callback Success will be called when the connection is successful.  Failure is called if the connection fails.

#### Android
For Android, `connect` takes a MAC address of the remote device.

### Parameters

- __address__: Identifier of the remote device.

### Quick Example

```typescript
BluetoothSerial
  .connect({
    address: '00:11:22:33:44:55',
  })
  .then(() => {
    console.log('Successfully connected')
  })
  .catch(() => {
    console.log('Error connecting...');
  });
```

## disconnect

Disconnect a Bluetooth device.

  `disconnect(options: BluetoothConnectOptions): Promise<void>`;

### Description

Function `disconnect` disconnects a Bluetooth device.  The callback Success will be called when the disconnection is successful.  Failure is called if the disconnection fails.

#### Android
For Android, `disconnect` takes a MAC address of the remote device.

**Warning**: If no address is passed, all devices will be disconnected.

### Parameters

- __address__: Identifier of the remote device.

### Quick Example

```typescript
BluetoothSerial
  .disconnect({
    address: '00:11:22:33:44:55',
  })
  .then(() => {
    console.log('Successfully disconnected')
  })
  .catch(() => {
    console.log('Error disconnecting...');
  });
```