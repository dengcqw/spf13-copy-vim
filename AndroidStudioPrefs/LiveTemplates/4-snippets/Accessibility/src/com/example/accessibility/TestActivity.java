package com.example.accessibility;

import com.example.accessibility.R;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (!MyAccessibilityUtils.isSettingOn(this))
        {
            MyAccessibilityUtils.gotoSetting(this);
        }
    }
}
