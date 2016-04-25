package com.github.chenyoca.snippet.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.github.chenyoca.snippet.utils.NetworkUtil;

public class NetworkStateChangeReceiver extends BroadcastReceiver {
  
    private static final String TAG = "NCR";

    public interface Listener{
        void onChanged(boolean isActive);
    }

    private Listener listener;

    public void setStateChangeListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "网络状态发生改变");
        boolean isNetworkActive = NetworkUtil.isAvailable(context);
        if(listener == null) return;
        listener.onChanged(isNetworkActive);
    }

    public void register(Activity activity){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(this, filter);
    }

    public void unregister(Activity activity){
        activity.unregisterReceiver(this);
    }

}