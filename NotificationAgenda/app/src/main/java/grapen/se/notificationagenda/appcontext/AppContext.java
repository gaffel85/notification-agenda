package grapen.se.notificationagenda.appcontext;

import android.content.Context;

import grapen.se.notificationagenda.NotificationDisplayController;
import grapen.se.notificationagenda.scheduler.Scheduler;
import se.grapen.notificationagendamodel.AppConfig;
import se.grapen.notificationagendacalendar.CalendarRepository;
import se.grapen.notificationagendamodel.EventNotificationStatusRegister;
import se.grapen.notificationagendacalendar.implementation.AndroidCalendarRepository;
import se.grapen.notificationagendamodel.implementation.SharedPreferenceAppConfig;
import se.grapen.notificationagendamodel.implementation.SharedPreferenceNotificationStatusRegister;

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
    private EventNotificationStatusRegister notificationStatusRegister;
    private NotificationDisplayController notificationDisplayController;
    private AppConfig appConfig;
    private Scheduler scheduler;

    public CalendarRepository getCalendarRepository(Context context) {
        if (calendarRepository == null) {
            calendarRepository = new AndroidCalendarRepository(context.getContentResolver());
        }
        return calendarRepository;
    }

    public EventNotificationStatusRegister getNoficationStatusRegister(Context context) {
        if (notificationStatusRegister == null) {
            notificationStatusRegister = new SharedPreferenceNotificationStatusRegister(context);
        }
        return notificationStatusRegister;
    }

    public NotificationDisplayController getNotificationDisplayController(Context context) {
        if (notificationDisplayController == null) {
            notificationDisplayController= new NotificationDisplayController(context, getCalendarRepository(context), getNoficationStatusRegister(context), getAppConfig(context));
        }
        return notificationDisplayController;
    }

    public AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new SharedPreferenceAppConfig(context);
        }
        return appConfig;
    }

    public Scheduler getScheduler(Context context) {
        if (scheduler == null) {
            scheduler = new Scheduler(getAppConfig(context));
        }
        return scheduler;
    }
}
