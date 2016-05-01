package com.rincliu.capturescreen.activity;

import java.io.File;

import com.rincliu.capturescreen.CaptureScreenUtil;
import com.rincliu.capturescreen.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_cap).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isExternalStorageWriteable())
                {
                    String path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screen_"
                            + System.currentTimeMillis() + ".png").getAbsolutePath();
                    CaptureScreenUtil.captureScreenToFile(path);
                    toast("Screen capture saved: " + path);
                }
                else
                {
                    toast("External Storage is not available.");
                }
            }
        });
    }

    private void toast(String str)
    {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    private boolean isExternalStorageWriteable()
    {
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            mExternalStorageWriteable = true;
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            mExternalStorageWriteable = false;
        }
        else
        {
            mExternalStorageWriteable = false;
        }
        return mExternalStorageWriteable;
    }

}
