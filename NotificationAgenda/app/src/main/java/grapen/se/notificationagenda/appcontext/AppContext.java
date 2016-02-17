package grapen.se.notificationagenda.appcontext;

import android.content.Context;

import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.calendar.androidcalendar.AndroidCalendarRepository;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;
import grapen.se.notificationagenda.notificationproducer.androidnotification.AndroidNotificationProducer;

/**
 * Created by ola on 17/02/16.
 */
public final class AppContext {

    private static class AppContextLoader {
        private static final AppContext INSTANCE = new AppContext();
    }

    private AppContext() {
        if (AppContextLoader.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    static AppContext getInstance() {
        return AppContextLoader.INSTANCE;
    }

    private CalendarRepository calendarRepository;
    private NotificationProducer notificationProducer;

    public CalendarRepository getCalendarRepository(Context context) {
        if (calendarRepository == null) {
            calendarRepository = new AndroidCalendarRepository(context.getContentResolver());
        }
        return calendarRepository;
    }

    public NotificationProducer getNotificationProducer(Context context) {
        if (notificationProducer == null) {
            notificationProducer = new AndroidNotificationProducer(context);
        }
        return notificationProducer;
    }
}
