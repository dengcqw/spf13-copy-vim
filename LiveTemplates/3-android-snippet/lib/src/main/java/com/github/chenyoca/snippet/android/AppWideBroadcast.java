package com.github.chenyoca.snippet.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-10-17
 * 应用内广播，与全局广播相比，提高性能和广播效率
 */
public class AppWideBroadcast {

    public static void send(Context context, Intent data){
        LocalBroadcastManager.getInstance(context).sendBroadcast(data);
    }

    public static void register(Context context,BroadcastReceiver receiver,String filter){
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver,new IntentFilter(filter));
    }

    public static void unregister(Context context,BroadcastReceiver receiver){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }
}
