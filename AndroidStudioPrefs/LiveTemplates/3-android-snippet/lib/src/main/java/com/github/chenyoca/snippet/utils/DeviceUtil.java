package com.github.chenyoca.snippet.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * User: 桥下一粒砂(chenyoca@gmail.com)
 * Date: 2013-10-23
 * Time: 20:54
 * 获取设备ID
 */
public class DeviceUtil {

    /**
     * 获取IMEI码。平板设备没有这个码，需要 READ_PHONE_STATE 权限
     * @param context
     * @return IMEI码
     */
    public static String IMEI(Context context){
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            String IMEI = TelephonyMgr.getDeviceId();
            return null == IMEI ? "" : IMEI;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * Pseudo-Unique ID
     * @return
     */
    public static String PUID(){
        String likeIMEI = "35" +
        Build.BOARD.length()%10
                + Build.BRAND.length()%10
                + Build.CPU_ABI.length()%10
                + Build.DEVICE.length()%10
                + Build.DISPLAY.length()%10
                + Build.HOST.length()%10
                + Build.ID.length()%10
                + Build.MANUFACTURER.length()%10
                + Build.MODEL.length()%10
                + Build.PRODUCT.length()%10
                + Build.TAGS.length()%10
                + Build.TYPE.length()%10
                + Build.USER.length()%10 ;
        return likeIMEI;
    }

    /**
     * MAC地址
     * @param context
     * @return
     */
    public static String MAC(Context context){
        try {
            WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            String MAC = wm.getConnectionInfo().getMacAddress();
            return null == MAC ? "" : MAC;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 蓝牙地址
     * @return
     */
    public static String BlueToothMAC(){
        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            String BT_MAC = adapter.getAddress();
            return null == BT_MAC ? "" : BT_MAC;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 组合以上各种信息的设备码
     * @param context
     * @return
     */
    public static String CombinedDeviceID(Context context){
        return new StringBuilder("PUID:").append(PUID()).append("  ")
                .append("IMEI:").append(IMEI(context)).append("  ")
                .append("MAC:").append(MAC(context)).append("  ")
                .append("BT-MAC:").append(BlueToothMAC()).toString();
    }
}
