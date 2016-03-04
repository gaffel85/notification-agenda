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
import grapen.se.notificationagenda.model.CalendarEvent;
import grapen.se.notificationagenda.receivers.DismissNotificationReceiver;

/**
 * Created by ola on 19/02/16.
 */
public class EventNotification {
    public static final String NOTIFICATION_DISMISS_INTENT_EXTRA_ID = "NOTIFICATION_DISMISS_INTENT_EXTRA_ID";

    private int id;
    private String title;
    private long startTs;
    private DateFormat timeFormat;
    private DateFormat dateFormat;

    public EventNotification(int id, String title, long startTs, DateFormat timeFormat, DateFormat dateFormat) {
        this.id = id;
        this.title = title;
        this.startTs = startTs;
        this.timeFormat = timeFormat;
        this.dateFormat = dateFormat;
    }

    public int getNotificationId() {
        return id;
    }

    public String getFormattedStartDate(Context androidContext) {
        Date startDate = new Date(startTs);

        if (isDateToday(startDate)) {
            return timeFormat.format(startDate);
        } else if (isDateTomorrow(startDate)) {
            return androidContext.getString(R.string.tomorrow_date_prefix) + " " + timeFormat.format(startDate);
        } else {
            String date = dateFormat.format(startDate) + " " + timeFormat.format(startDate);
            return dateFormat.format(startDate);
        }
    }

    private boolean isDateTomorrow(Date date) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        int dateDay = dateCalendar.get(Calendar.DAY_OF_MONTH);

        Date today = new Date();
        Calendar tomorrowCalendar = Calendar.getInstance();
        tomorrowCalendar.setTime(today);
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1);

        return tomorrowCalendar.get(Calendar.DAY_OF_MONTH) == dateDay;
    }

    private boolean isDateToday(Date date) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        int dateDay = dateCalendar.get(Calendar.DAY_OF_MONTH);

        Date today = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(today);

        return todayCalendar.get(Calendar.DAY_OF_MONTH) == dateDay;
    }

    public Notification build(Context androidContext) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
        builder.setContentTitle(title);
        builder.setContentText(getFormattedStartDate(androidContext));
        builder.setSmallIcon(R.drawable.notification_icon);

        int intentId = id;
        Intent intent = new Intent(androidContext, DismissNotificationReceiver.class);
        intent.putExtra(EventNotification.NOTIFICATION_DISMISS_INTENT_EXTRA_ID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(androidContext, intentId, intent, 0);
        builder.setDeleteIntent(pendingIntent);

        return builder.build();
    }
}
