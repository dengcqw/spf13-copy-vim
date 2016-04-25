package com.example.plugin;

import android.content.Context;
import android.widget.Toast;

public class Module
{
    public void func(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
