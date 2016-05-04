package com.rincliu.polling.activity;

import com.rincliu.polling.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends Activity
{
    public static final String EXTRA_DATA = "EXTRA_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("New Message!");
        ((TextView) findViewById(R.id.tv_message)).setText(getIntent().getStringExtra(EXTRA_DATA));
    }
}
