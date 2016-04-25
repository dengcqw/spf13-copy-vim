package com.rincliu.polling;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;

public class PollingService extends Service
{
    public static final String ACTION = "com.rincliu.service.PollingService";

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        new AsyncTask<Void, Void, String>()
        {

            @Override
            protected String doInBackground(Void... params)
            {
                String result = null;
                try
                {
                    HttpResponse response = new DefaultHttpClient().execute(new HttpGet("http://RincLiu.com/atom.xml"));
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && response.getEntity() != null)
                    {
                        result = EntityUtils.toString(response.getEntity());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result)
            {
                if (!TextUtils.isEmpty(result))
                {
                    Util.showNotification(PollingService.this, "New Message!", result);
                }
            }

        }.execute();
    }
}
