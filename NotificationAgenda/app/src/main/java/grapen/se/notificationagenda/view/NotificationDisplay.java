package grapen.se.notificationagenda.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;

import grapen.se.notificationagenda.R;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationDisplay {

    public interface Adapter {
        int nbrOfEvents();
        int getIdForEvent(int eventIndex);
        String getTitleForEvent(int eventIndex);
        long getStartTimestampForEvent(int eventIndex);
    }

    private Context androidContext;
    private Adapter adapter;
    private DateFormat timeFormat;
    private DateFormat dateFormat;

    public NotificationDisplay(Context androidContext, DateFormat timeFormat, DateFormat dateFormat, Adapter adapter) {
        this.androidContext = androidContext;
        this.adapter = adapter;
        this.timeFormat = timeFormat;
        this.dateFormat = dateFormat;
    }

    public void displayEventNotifications() {
        NotificationManager notificationManager = (NotificationManager) androidContext.getSystemService(androidContext.NOTIFICATION_SERVICE);

        if (adapter.nbrOfEvents() == 0) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
            builder.setContentTitle("No Events");
            builder.setSmallIcon(R.drawable.notification_icon);
            notificationManager.notify(99999999, builder.build());

            return;
        } else {
            for (int i = 0; i < adapter.nbrOfEvents(); i++) {
                int id = adapter.getIdForEvent(i);
                String title = adapter.getTitleForEvent(i);
                long startTs = adapter.getStartTimestampForEvent(i);
                EventNotification eventNotification = new EventNotification(id, title, startTs, timeFormat, dateFormat);
                Notification notification = eventNotification.build(androidContext);
                notificationManager.notify(eventNotification.getNotificationId(), notification);
            }
        }
    }
}
