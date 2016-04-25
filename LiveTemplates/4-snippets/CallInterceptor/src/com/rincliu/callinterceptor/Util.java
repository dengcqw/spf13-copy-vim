package com.rincliu.callinterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class Util
{
    public static void endCall(Context context)
    {
        Class<TelephonyManager> c = TelephonyManager.class;
        try
        {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            iTelephony = (ITelephony) getITelephonyMethod.invoke(
                    (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE), (Object[]) null);
            iTelephony.endCall();
            Log.d("@", "Ended call.");
        }
        catch (Exception e)
        {
            Log.d("@", "Failed to end call.", e);
        }
    }

    public static ArrayList<String> getLocalPhoneNumberList(Context context)
    {
        ArrayList<String> numList = new ArrayList<String>();
        
        ContentResolver cr = context.getContentResolver();
        Cursor listCursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (listCursor.moveToNext())
        {
            String contactId = listCursor.getString(listCursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor contactCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (contactCursor.moveToNext())
            {
                String num = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numList.add(num.replace(" ", ""));
                Log.d("@", "LocalPhoneNumber:" + num);
            }
            contactCursor.close();
        }
        listCursor.close();
        
        return numList;
    }
}
