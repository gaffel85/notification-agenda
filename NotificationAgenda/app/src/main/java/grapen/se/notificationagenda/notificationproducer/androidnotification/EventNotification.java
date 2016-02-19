package grapen.se.notificationagenda.notificationproducer.androidnotification;

import grapen.se.notificationagenda.calendar.CalendarEvent;

/**
 * Created by ola on 19/02/16.
 */
public class EventNotification {
    public static final String NOTIFICATION_DISMISS_INTENT_EXTRA_ID = "NOTIFICATION_DISMISS_INTENT_EXTRA_ID";
    private CalendarEvent event;

    public EventNotification(CalendarEvent event) {
        this.event = event;
    }

    public int getNotificationId() {
        return (int) event.getId();
    }
}
