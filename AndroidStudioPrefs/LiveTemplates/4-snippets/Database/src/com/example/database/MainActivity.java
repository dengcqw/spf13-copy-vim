package com.example.database;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv=(TextView) findViewById(R.id.tv);
        StringBuilder sb=new StringBuilder("");
        
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.database.my_data_provider/peoples");
        
        ContentValues values=new ContentValues();
        values.put("name", "Rinc");
        values.put("age", ""+System.currentTimeMillis());
        resolver.insert(uri, values);
        
        values.put("name", "Rinc Liu");
        resolver.update(uri, values, "name='Rinc'", null);
        
        Cursor cursor = resolver.query(uri, new String[] {"_id", "name", "age"}, null, null, "_id desc");
        while (cursor.moveToNext())
        {
            sb.append("name=").append(cursor.getString(1))
            .append(", age=").append(cursor.getString(2))
            .append("\n");
        }
        cursor.close();
        tv.setText(sb);
    }
}
