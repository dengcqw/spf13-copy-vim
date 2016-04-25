package com.rincliu.callinterceptor.activity;

import java.util.ArrayList;

import com.rincliu.callinterceptor.Util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{

    private ArrayList<String> numList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final ListView lv = new ListView(this);
        setContentView(lv);

        lv.setAdapter(new BaseAdapter()
        {

            @Override
            public int getCount()
            {
                return numList.size();
            }

            @Override
            public Object getItem(int position)
            {
                return position < numList.size() ? numList.get(position) : null;
            }

            @Override
            public long getItemId(int position)
            {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                TextView tv = new TextView(MainActivity.this);
                tv.setText(numList.get(position));
                tv.setPadding(10, 10, 10, 10);
                tv.setTextSize(20);
                return tv;
            }

        });

        new AsyncTask<Context, Void, ArrayList<String>>()
        {

            @Override
            protected ArrayList<String> doInBackground(Context... params)
            {
                return Util.getLocalPhoneNumberList(params[0]);
            }

            @Override
            protected void onPostExecute(ArrayList<String> result)
            {
                if (result != null && !result.isEmpty())
                {
                    if (!numList.isEmpty())
                    {
                        numList.clear();
                    }
                    numList.addAll(result);
                    result.clear();
                    ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
                }
            }

        }.execute(this);
    }

}
