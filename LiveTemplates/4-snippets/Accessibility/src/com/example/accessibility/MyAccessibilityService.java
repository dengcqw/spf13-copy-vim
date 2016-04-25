package com.example.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService
{

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onServiceConnected()
    {
        Log.d("@", "Accessibility Service connected.");
        
        AccessibilityServiceInfo accessibilityServiceInfo = getServiceInfo();

        if (accessibilityServiceInfo == null)
        {
            accessibilityServiceInfo = new AccessibilityServiceInfo();
        }

        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        accessibilityServiceInfo.packageNames = new String[] {getPackageName()};
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        accessibilityServiceInfo.notificationTimeout = 10;

        setServiceInfo(accessibilityServiceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)
    {
        switch (event.getEventType())
        {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
            {
                Log.d("@", "View clicked.");
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
            {
                Log.d("@", "View focused.");
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
            {
                Log.d("@", "View text changed.");
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
            {
                Log.d("@", "View text selection changed.");
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
            {
                Log.d("@", "View scrolled.");
                break;
            }
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            {
                Log.d("@", "Window content changed.");
                break;
            }
        }
    }

    @Override
    public void onInterrupt()
    {
        // TODO Auto-generated method stub

    }

}
