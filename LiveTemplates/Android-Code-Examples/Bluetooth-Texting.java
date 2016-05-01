public class BluetoothTexting extends Activity {

  private static int DISCOVERY_REQUEST = 1;

  private Handler handler = new Handler();
  private ArrayList<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
  private ArrayAdapter<BluetoothDevice> aa;
  private ListView list;

  private BluetoothAdapter bluetooth;
  private BluetoothSocket socket;
  private UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");



  BluetoothDevice demo_remoteDevice;
  Set<BluetoothDevice> bondedDevices;
  String remoteDeviceAddress = "";//demo

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Get the Bluetooth Adapter
    configureBluetooth();

    // Setup the ListView of discovered devices
    setupListView();

    // Setup search button
    setupSearchButton();

    // Setup listen button
    setupListenButton();
  }

  private void configureBluetooth() {
    bluetooth = BluetoothAdapter.getDefaultAdapter();
    String toastText;
    if (bluetooth.isEnabled()) {
      toastText = bluetooth.getName() + " : " + bluetooth.getAddress();
    } else {
      toastText = "No bluetooth";
  
      String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
      String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
      registerReceiver(bluetoothState, new IntentFilter(actionStateChanged));
      startActivityForResult(new Intent(actionRequestEnable), 0);
    }

    Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();

    
    registerReceiver(discoveryMonitor, new IntentFilter(dStarted));
    registerReceiver(discoveryMonitor, new IntentFilter(dFinished));

 
    registerReceiver(discoveryResult2, new IntentFilter(
        BluetoothDevice.ACTION_FOUND));
    if (!bluetooth.isDiscovering())
      bluetooth.startDiscovery();
  }

  private void setupListenButton() {
    Button listenButton = (Button) findViewById(R.id.button_listen);
    listenButton.setOnClickListener(new OnClickListener() {
      public void onClick(View view) {
        Intent disc = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(disc, DISCOVERY_REQUEST);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == DISCOVERY_REQUEST) {
      boolean isDiscoverable = resultCode > 0;
      if (isDiscoverable) {
        list.setVisibility(View.VISIBLE);
        String name = "bluetoothserver";
        try {
          final BluetoothServerSocket btserver = bluetooth
              .listenUsingRfcommWithServiceRecord(name, uuid);

          AsyncTask<Integer, Void, BluetoothSocket> acceptThread = new AsyncTask<Integer, Void, BluetoothSocket>() {

            @Override
            protected BluetoothSocket doInBackground(Integer... params) {
              try {
                socket = btserver.accept(params[0] * 1000);
                return socket;
              } catch (IOException e) {
                Log.d("BLUETOOTH", e.getMessage());
              }
              return null;
            }

            @Override
            protected void onPostExecute(BluetoothSocket result) {
              if (result != null)
                switchUI();
            }
          };
          acceptThread.execute(resultCode);
        } catch (IOException e) {
          Log.d("BLUETOOTH", e.getMessage());
        }
      }
    }
  }

  private void setupListView() {
    aa = new ArrayAdapter<BluetoothDevice>(this,
        android.R.layout.simple_list_item_1, foundDevices);
    list = (ListView) findViewById(R.id.list_discovered);
    list.setAdapter(aa);

    list.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> arg0, View view, int index,
          long arg3) {

        Toast.makeText(BluetoothTexting.this, foundDevices.get(index).getName(), Toast.LENGTH_SHORT).show();

        AsyncTask<Integer, Void, Void> connectTask = new AsyncTask<Integer, Void, Void>() {
          @Override
          protected Void doInBackground(Integer... params) {
            try {
              BluetoothDevice device = foundDevices.get(params[0]);
              socket = device.createRfcommSocketToServiceRecord(uuid);
              socket.connect();
            } catch (IOException e) {
              Log.d("BLUETOOTH_CLIENT", e.getMessage());
            }
            return null;
          }

          @Override
          protected void onPostExecute(Void result) {
            switchUI();
          }
        };
        connectTask.execute(index);
      }
    });
  }

  private void setupSearchButton() {
    Button searchButton = (Button) findViewById(R.id.button_search);

    searchButton.setOnClickListener(new OnClickListener() {
      public void onClick(View view) {
        registerReceiver(discoveryResult, new IntentFilter(
            BluetoothDevice.ACTION_FOUND));

        if (!bluetooth.isDiscovering()) {
          foundDevices.clear();
          bluetooth.startDiscovery();
        }
      }
    });
  }

  private void switchUI() {
    final TextView messageText = (TextView) findViewById(R.id.text_messages);
    final EditText textEntry = (EditText) findViewById(R.id.text_message);

    messageText.setVisibility(View.VISIBLE);
    list.setVisibility(View.GONE);
    textEntry.setEnabled(true);

    textEntry.setOnKeyListener(new OnKeyListener() {
      public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            && (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
          sendMessage(socket, textEntry.getText().toString());
          textEntry.setText("");
          return true;
        }
        return false;
      }
    });
    BluetoothSocketListener bsl = new BluetoothSocketListener(socket, handler,
        messageText);
    Thread messageListener = new Thread(bsl);
    messageListener.start();
  }

  private void sendMessage(BluetoothSocket socket, String msg) {
    OutputStream outStream;
    try {
      outStream = socket.getOutputStream();
      byte[] byteString = (msg + " ").getBytes();
      byteString[byteString.length - 1] = 0;
      outStream.write(byteString);
    } catch (IOException e) {
      Log.d("BLUETOOTH_COMMS", e.getMessage());
    }
  }

  BroadcastReceiver discoveryResult = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      BluetoothDevice remoteDevice;
      remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
      if (bluetooth.getBondedDevices().contains(remoteDevice)) {
        foundDevices.add(remoteDevice);
        aa.notifyDataSetChanged();
      }
    }
  };

  private class MessagePoster implements Runnable {
    private TextView textView;
    private String message;

    public MessagePoster(TextView textView, String message) {
      this.textView = textView;
      this.message = message;
    }

    public void run() {
      textView.setText(message);
    }
  }

  private class BluetoothSocketListener implements Runnable {

    private BluetoothSocket socket;
    private TextView textView;
    private Handler handler;

    public BluetoothSocketListener(BluetoothSocket socket, Handler handler,
        TextView textView) {
      this.socket = socket;
      this.textView = textView;
      this.handler = handler;
    }

    public void run() {
      int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];
      try {
        InputStream instream = socket.getInputStream();
        int bytesRead = -1;
        String message = "";
        while (true) {
          message = "";
          bytesRead = instream.read(buffer);
          if (bytesRead != -1) {
            while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
              message = message + new String(buffer, 0, bytesRead);
              bytesRead = instream.read(buffer);
            }
            message = message + new String(buffer, 0, bytesRead - 1);

            handler.post(new MessagePoster(textView, message));
            socket.getInputStream();
          }
        }
      } catch (IOException e) {
        Log.d("BLUETOOTH_COMMS", e.getMessage());
      }
    }
  }

  
  BroadcastReceiver bluetoothState = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      String prevStateExtra = BluetoothAdapter.EXTRA_PREVIOUS_STATE;
      String stateExtra = BluetoothAdapter.EXTRA_STATE;
      int state = intent.getIntExtra(stateExtra, -1);
      int previousState = intent.getIntExtra(prevStateExtra, -1);
      String tt = "";
      switch (state) {
      case (BluetoothAdapter.STATE_TURNING_ON): {
        tt = "Bluetooth turning on";
        break;
      }
      case (BluetoothAdapter.STATE_ON): {
        tt = "Bluetooth on";
        unregisterReceiver(this);
        break;
      }
      case (BluetoothAdapter.STATE_TURNING_OFF): {
        tt = "Bluetooth turning off";
        break;
      }
      case (BluetoothAdapter.STATE_OFF): {
        tt = "Bluetooth off";
        break;
      }
      default:
        break;
      }
      Toast.makeText(context, tt, Toast.LENGTH_LONG).show();
    }
  };

  String dStarted = BluetoothAdapter.ACTION_DISCOVERY_STARTED;
  String dFinished = BluetoothAdapter.ACTION_DISCOVERY_FINISHED;

  BroadcastReceiver discoveryMonitor = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (dStarted.equals(intent.getAction())) {
        // Discoveryhasstarted.
        Toast.makeText(getApplicationContext(), "Discovery Started ... ",
            Toast.LENGTH_SHORT).show();
      } else if (dFinished.equals(intent.getAction())) {
        // Discoveryhascompleted.
        Toast.makeText(getApplicationContext(), "Discovery Completed ... ",
            Toast.LENGTH_SHORT).show();
      }
    }
  };

  // registerReceiver(discoveryMonitor,new IntentFilter(dStarted));
  // registerReceiver(discoveryMonitor,new IntentFilter(dFinished));

  BroadcastReceiver discoveryResult2 = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String remoteDeviceName = intent
          .getStringExtra(BluetoothDevice.EXTRA_NAME);

      BluetoothDevice remoteDevice;
      remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
      remoteDeviceAddress = remoteDevice.getAddress();
      Toast.makeText(getApplicationContext(), "Discovered:" + remoteDeviceName,
          Toast.LENGTH_SHORT).show();
      // TODODosomethingwiththeremoteBluetoothDevice.

      
      demo_remoteDevice = bluetooth.getRemoteDevice(remoteDeviceAddress);//"01:23:77:35:2F:AA");
      bondedDevices = bluetooth.getBondedDevices();
      //
      registerReceiver(discoveryPairResult,
          new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }
  };
  // registerReceiver(discoveryResult,
  // newIntentFilter(BluetoothDevice.ACTION_FOUND));
  // if(!bluetooth.isDiscovering())
  // bluetooth.startDiscovery();

  BroadcastReceiver discoveryPairResult = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      BluetoothDevice remoteDevice = intent
          .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
      if (remoteDevice.equals(remoteDevice) && (bondedDevices.contains(remoteDevice))) {
        // TODO
        //Target device is paired and discoverable

        Toast.makeText(getApplicationContext(), "xxxxxxxxxx:" + remoteDevice.getName(),
            Toast.LENGTH_SHORT).show();

      }
    };
  };
}
