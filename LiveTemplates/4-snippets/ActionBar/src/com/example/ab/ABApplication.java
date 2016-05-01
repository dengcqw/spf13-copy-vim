package com.example.ab;

import com.example.ab.util.ActionBarUtils;

import android.app.Application;

public class ABApplication extends Application
{
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        ActionBarUtils.enableOverflowMenu(this);
    }

}
