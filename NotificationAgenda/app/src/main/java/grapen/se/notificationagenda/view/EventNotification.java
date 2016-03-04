package grapen.se.notificationagenda.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import grapen.se.notificationagenda.R;
import grapen.se.notificationagenda.receivers.DismissNotificationReceiver;

/**
 * Created by ola on 19/02/16.
 */
public class EventNotification {
    public static final String NOTIFICATION_DISMISS_INTENT_EXTRA_ID = "NOTIFICATION_DISMISS_INTENT_EXTRA_ID";

    private int id;
    private String title;
    private long startTs;

    public EventNotification(int id, String title, long startTs) {
        this.id = id;
        this.title = title;
        this.startTs = startTs;
    }

    public int getNotificationId() {
        return id;
    }

    public Notification build(Context androidContext) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
        builder.setContentTitle(title);
        builder.setContentText("00");
        builder.setSmallIcon(R.drawable.notification_icon);

        int intentId = id;
        Intent intent = new Intent(androidContext, DismissNotificationReceiver.class);
        intent.putExtra(EventNotification.NOTIFICATION_DISMISS_INTENT_EXTRA_ID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(androidContext, intentId, intent, 0);
        builder.setDeleteIntent(pendingIntent);

        return builder.build();
    }
}
