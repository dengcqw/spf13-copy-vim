package com.example.keyboardlayout;

import com.example.keyboardlayout.KeyboardLayout.OnKeyboardListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyboardLayout kl = (KeyboardLayout) findViewById(R.id.keyboard);
        final ScrollView sv = (ScrollView) findViewById(R.id.scroll);
        kl.setOnKeyboardListener(new OnKeyboardListener(){
            @Override
            public void onShown()
            {
                sv.postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {
                        sv.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 100);
            }
            @Override
            public void onHidden()
            {
            }
        });
    }
}