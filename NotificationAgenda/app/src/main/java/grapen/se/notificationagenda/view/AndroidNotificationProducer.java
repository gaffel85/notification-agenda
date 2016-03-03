package grapen.se.notificationagenda.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;
import java.util.List;

import grapen.se.notificationagenda.model.AppConfig;
import grapen.se.notificationagenda.receivers.DismissNotificationReceiver;
import grapen.se.notificationagenda.R;
import grapen.se.notificationagenda.model.CalendarEvent;

/**
 * Created by ola on 17/02/16.
 */
public class AndroidNotificationProducer implements NotificationProducer {

    private Context androidContext;
    private AppConfig config;

    public AndroidNotificationProducer(Context androidContext, AppConfig config) {
        this.androidContext = androidContext;
        this.config = config;
    }

    @Override
    public void produce(List<CalendarEvent> events) {
        NotificationManager notificationManager = (NotificationManager) androidContext.getSystemService(androidContext.NOTIFICATION_SERVICE);

        if (events.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
            builder.setContentTitle("No Events");
            builder.setSmallIcon(R.drawable.notification_icon);
            notificationManager.notify(99999999, builder.build());

            return;
        }

        for (CalendarEvent event : events) {
            EventNotification notification = new EventNotification(event);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
            builder.setContentTitle(event.getDisplayName());
            builder.setContentText(event.getStartDateFormatted(new NotificationTimeStringPovider(), config.runCalenderCheckAtHour()));
            builder.setSmallIcon(R.drawable.notification_icon);

            int intentId = notification.getNotificationId();
            Intent intent = new Intent(androidContext, DismissNotificationReceiver.class);
            intent.putExtra(EventNotification.NOTIFICATION_DISMISS_INTENT_EXTRA_ID, notification.getNotificationId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(androidContext, intentId, intent, 0);
            builder.setDeleteIntent(pendingIntent);

            notificationManager.notify(intentId, builder.build());
        }


    }

    class NotificationTimeStringPovider implements CalendarEvent.FormatDateStringProvider {

        @Override
        public String getTomorrowString() {
            return androidContext.getString(R.string.tomorrow_date_prefix);
        }

        @Override
        public DateFormat getTimeFormat() {
            return android.text.format.DateFormat.getTimeFormat(androidContext);
        }

        @Override
        public DateFormat getDateFormat() {
            return android.text.format.DateFormat.getMediumDateFormat(androidContext);
        }
    }


}
