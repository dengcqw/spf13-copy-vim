package com.rincliu.polling;

import com.rincliu.polling.activity.MessageActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class Util
{
    public static void startPollingService(Context context, long requestCircleMillis)
    {
        getAlarmManager(context).setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
                requestCircleMillis, getPollingPendingIntent(context));
    }

    public static void stopPollingService(Context context)
    {
        getAlarmManager(context).cancel(getPollingPendingIntent(context));
    }

    public static void showNotification(Context context, String title, String content)
    {
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText = title;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();

        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(MessageActivity.EXTRA_DATA, content);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

        CharSequence msg = content.length() > 20 ? content.subSequence(0, 20) : content;
        notification.setLatestEventInfo(context, title, msg, pendingIntent);
        getNotificationManager(context).notify(0, notification);
    }

    private static NotificationManager getNotificationManager(Context context)
    {
        return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    private static AlarmManager getAlarmManager(Context context)
    {
        return ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    private static PendingIntent getPollingPendingIntent(Context context)
    {
        Intent intent = new Intent(context, PollingService.class);
        intent.setAction(PollingService.ACTION);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
