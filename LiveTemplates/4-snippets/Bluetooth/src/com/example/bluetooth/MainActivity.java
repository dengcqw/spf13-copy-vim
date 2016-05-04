package com.example.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.example.bluetooth.BluetoothHelper.OnDiscoveryListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;

public class MainActivity extends Activity
{
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    
    private BluetoothAdapter btAdapter;

    private HashSet<String> addrs = new HashSet<String>();

    private ArrayList<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();

    BroadcastReceiver mReceiver;

    private BaseAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        ListView lv = new ListView(this);
        lv.setCacheColorHint(Color.TRANSPARENT);
        setContentView(lv);
        lvAdapter = new BaseAdapter()
        {
            private Context context = MainActivity.this;

            @Override
            public int getCount()
            {
                return deviceList == null || deviceList.isEmpty() ? 0 : deviceList.size();
            }

            @Override
            public Object getItem(int position)
            {
                return deviceList == null || deviceList.isEmpty() ? null : deviceList.get(position);
            }

            @Override
            public long getItemId(int position)
            {
                return position;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent)
            {
                if (deviceList == null || deviceList.isEmpty())
                {
                    return null;
                }
                else
                {
                    BluetoothDevice device = deviceList.get(position);
                    TextView tv = new TextView(context);
                    tv.setTextSize(20);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(device.getName() + "(" + device.getAddress() + ")" + ", " + getBondState(device));
                    return tv;
                }
            }
        };
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (btAdapter.isDiscovering())
                {
                    btAdapter.cancelDiscovery();
                    setProgressBarIndeterminateVisibility(false);
                }
                
                final BluetoothDevice device = deviceList.get(position);
                
                new Thread(){
                    @Override
                    public void run(){
                        BluetoothSocket btSocket=null;
                        try
                        {
                            btSocket=device.createRfcommSocketToServiceRecord(MY_UUID);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            handler.sendEmptyMessage(MSG_CONNECT_FAILED);
                        }
                        try
                        {
                            btSocket.connect();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            handler.sendEmptyMessage(MSG_CONNECT_FAILED);
                            try
                            {
                                btSocket.close();
                            }
                            catch (IOException e1)
                            {
                                e1.printStackTrace();
                                handler.sendEmptyMessage(MSG_CONNECT_FAILED);
                            }
                        }
                    }
                }.start();
            }
        });

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null)
        {
            setTitle(btAdapter.getName() + "(" + btAdapter.getAddress() + ")");

            // Intent discoverableIntent = new
            // Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            // discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
            // 300);
            // startActivity(discoverableIntent);

            if (!btAdapter.isEnabled())
            {
                btAdapter.enable();
                // Intent intent = new
                // Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // startActivityForResult(intent, REQUEST_ENABLE);
            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            scanDevices();
        }
        else
        {
            Toast.makeText(this, "Not supported.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mReceiver != null)
        {
            unregisterReceiver(mReceiver);
        }
        if (btAdapter.isDiscovering())
        {
            btAdapter.cancelDiscovery();
            setProgressBarIndeterminateVisibility(false);
        }
        if (btAdapter.isEnabled())
        {
            btAdapter.disable();
        }
        addrs.clear();
        deviceList.clear();
    }

    private void scanDevices()
    {
        Set<BluetoothDevice> set = btAdapter.getBondedDevices();
        if (set != null && !set.isEmpty())
        {
            Iterator<BluetoothDevice> i = set.iterator();
            while (i.hasNext())
            {
                BluetoothDevice device = i.next();
                onFoundDevice(device);
            }
            lvAdapter.notifyDataSetChanged();
        }
        mReceiver = new BluetoothHelper().discovery(this, new OnDiscoveryListener()
        {
            @Override
            public void onDiscoveryStarted()
            {
                setProgressBarIndeterminateVisibility(true);
                Toast.makeText(MainActivity.this, "Discovery started.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceFound(BluetoothDevice device)
            {
                onFoundDevice(device);
            }

            @Override
            public void onDiscoveryFinished()
            {
                setProgressBarIndeterminateVisibility(false);
                Toast.makeText(MainActivity.this, "Discovery finished.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getBondState(BluetoothDevice device)
    {
        String res = null;
        switch (device.getBondState())
        {
            case BluetoothDevice.BOND_BONDED:
                res = "bonded";
                break;
            case BluetoothDevice.BOND_BONDING:
                res = "bonding";
                break;
            case BluetoothDevice.BOND_NONE:
                res = "not bonded";
                break;
        }
        return res;
    }

    private void onFoundDevice(BluetoothDevice device)
    {
        if (!addrs.contains(device.getAddress()))
        {
            addrs.add(device.getAddress());
            deviceList.add(device);
            lvAdapter.notifyDataSetChanged();
        }
    }
    
    private static final int MSG_CONNECT_FAILED=0x8888;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            Context context=MainActivity.this;
            switch(msg.what){
                case MSG_CONNECT_FAILED:
                    Toast.makeText(context, "Failed to connect.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
