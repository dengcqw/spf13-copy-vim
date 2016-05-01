package com.example.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;

public class MyAccessibilityUtils
{

    public static void gotoSetting(Context context)
    {
        context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }

    public static boolean isSettingOn(Context context)
    {
        final String MY_ACCESSIBILITY_SERVICE = context.getPackageName() + "/" + MyAccessibilityService.class.getName();
        final ContentResolver cResolver = context.getApplicationContext().getContentResolver();

        int accessibilityEnabled = -1;
        try
        {
            accessibilityEnabled = Settings.Secure.getInt(cResolver, Settings.Secure.ACCESSIBILITY_ENABLED);
        }
        catch (SettingNotFoundException e)
        {
        }

        if (accessibilityEnabled == 1)
        {
            String settingValue = Settings.Secure.getString(cResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null)
            {
                TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
                splitter.setString(settingValue);
                while (splitter.hasNext())
                {
                    String s = splitter.next();
                    if (MY_ACCESSIBILITY_SERVICE.equals(s))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
