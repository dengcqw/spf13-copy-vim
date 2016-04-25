package com.rincliu.callinterceptor;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallStateReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        switch (((TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE)).getCallState())
        {
            case TelephonyManager.CALL_STATE_IDLE:
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("@", "Incoming call:" + number);
                if (!Util.getLocalPhoneNumberList(context).contains(number))
                {
                    Util.endCall(context);
                    //TODO
                }
        }
    }
}
