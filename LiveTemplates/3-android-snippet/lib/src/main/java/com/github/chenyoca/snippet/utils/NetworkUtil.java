package com.github.chenyoca.snippet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date   : 2012-11-1
 * 网络工具类
 */
public class NetworkUtil {

	/**
	 * 返回网络是否可用。需要权限：
	 * <p><b> < uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> </b></p>
	 * @param context 上下文
	 * @return 网络可用则返回true，否则返回false
	 */
	public static boolean isAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable();
	}
	
	/**
	 * 返回Wifi是否启用
	 * @param context 上下文
	 * @return Wifi网络可用则返回true，否则返回false
	 */
	public static boolean isWIFIActivate(Context context) {
		return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).isWifiEnabled();
	}
	
	/**
	 * 修改Wifi状态
	 * @param context 上下文
	 * @param status true为开启Wifi，false为关闭Wifi
	 */
	public static void changeWIFIStatus(Context context, boolean status) {
		((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(status);
	}
}
