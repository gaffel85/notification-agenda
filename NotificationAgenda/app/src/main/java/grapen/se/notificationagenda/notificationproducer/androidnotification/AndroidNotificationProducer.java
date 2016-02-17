package grapen.se.notificationagenda.notificationproducer.androidnotification;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import grapen.se.notificationagenda.R;
import grapen.se.notificationagenda.calendar.CalendarEvent;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;

/**
 * Created by ola on 17/02/16.
 */
public class AndroidNotificationProducer implements NotificationProducer {

    private Context androidContext;

    public AndroidNotificationProducer(Context androidContext) {
        this.androidContext = androidContext;
    }

    @Override
    public void produce(List<CalendarEvent> events) {
        NotificationManager notificationManager = (NotificationManager) androidContext.getSystemService(androidContext.NOTIFICATION_SERVICE);

        for (CalendarEvent event : events) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
            builder.setContentTitle(event.getDisplayName());
            builder.setContentText(event.getStartDateFormatted());
            builder.setSmallIcon(R.drawable.icon);

            int notificationId = getIdForEvent(event);

            notificationManager.notify(notificationId, builder.build());
        }


    }

    @Override
    public int getIdForEvent(CalendarEvent event) {
        return (int) event.getId();
    }
}
