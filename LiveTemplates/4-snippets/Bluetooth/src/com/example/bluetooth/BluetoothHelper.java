package com.example.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BluetoothHelper
{

    public interface OnDiscoveryListener
    {
        public void onDiscoveryStarted();

        public void onDeviceFound(BluetoothDevice device);

        public void onDiscoveryFinished();
    }

    public BroadcastReceiver discovery(Context context, final OnDiscoveryListener onDiscoveryListener)
    {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null && btAdapter.isEnabled() && !btAdapter.isDiscovering())
        {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

            BroadcastReceiver mReceiver = new BroadcastReceiver()
            {
                public void onReceive(Context context, Intent intent)
                {
                    if (onDiscoveryListener == null)
                    {
                        return;
                    }
                    String action = intent.getAction();
                    if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
                    {
                        onDiscoveryListener.onDiscoveryStarted();
                    }
                    else if (BluetoothDevice.ACTION_FOUND.equals(action))
                    {
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        onDiscoveryListener.onDeviceFound(device);
                    }
                    else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
                    {
                        onDiscoveryListener.onDiscoveryFinished();
                    }
                }
            };

            context.registerReceiver(mReceiver, filter);
            btAdapter.startDiscovery();
            return mReceiver;
        }
        return null;
    }
}
