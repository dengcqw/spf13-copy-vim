package com.example.host;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
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

        findViewById(R.id.btn).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("com.rincliu.plugin");
                List<ResolveInfo> plugins = getPackageManager().queryIntentActivities(intent, 0);

                if (plugins != null && plugins.size() > 0)
                {
                    ActivityInfo act = plugins.get(0).activityInfo;
                    String packageName = act.packageName;
                    ApplicationInfo app = act.applicationInfo;

                    String dexSourceDir = app.sourceDir;
                    String dexOutputDir = getApplicationInfo().dataDir;
                    String dexLibDir = app.nativeLibraryDir;
                    ClassLoader parentLoader = this.getClass().getClassLoader();
                    DexClassLoader loader = new DexClassLoader(dexSourceDir, dexOutputDir, dexLibDir, parentLoader);

                    try
                    {
                        Class<?> clazz = loader.loadClass(packageName + ".Module");
                        
                        //Object obj = clazz.newInstance();
                        
                        Constructor<?> localConstructor = clazz.getConstructor();
                        Object obj = localConstructor.newInstance();
                        
                        //Method method = ((Class<?>) obj).getMethod("func", Context.class, String.class);
                        
                        Method method = clazz.getDeclaredMethod("func", Context.class, String.class);
                        
                        method.invoke(obj, MainActivity.this, "Hello, DexClassLoader.");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No plugin installed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
