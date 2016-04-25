package com.rincliu.polling.activity;

import com.rincliu.polling.Util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Start polling...", Toast.LENGTH_LONG).show();
        Util.startPollingService(this, 5 * 1000);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Stop polling...", Toast.LENGTH_LONG).show();
        Util.stopPollingService(this);
    }

}
