package grapen.se.notificationagenda.appcontext;

import android.content.Context;

import grapen.se.notificationagenda.NotificationDisplayController;
import grapen.se.notificationagenda.viewmodel.NotificationDisplayVM;
import grapen.se.notificationagenda.scheduler.Scheduler;
import grapen.se.notificationagenda.model.CalendarRepository;
import grapen.se.notificationagenda.model.implementation.AndroidCalendarRepository;
import grapen.se.notificationagenda.model.AppConfig;
import grapen.se.notificationagenda.model.implementation.SharedPreferenceAppConfig;
import grapen.se.notificationagenda.model.EventNotificationStatusRegister;
import grapen.se.notificationagenda.model.implementation.SharedPreferenceNotificationStatusRegister;

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
    private NotificationDisplayVM notificationDisplayVM;
    private NotificationDisplayController notificationDisplayController;
    private AppConfig appConfig;
    private Scheduler scheduler;

    public CalendarRepository getCalendarRepository(Context context) {
        if (calendarRepository == null) {
            calendarRepository = new AndroidCalendarRepository(context.getContentResolver(), getAppConfig(context));
        }
        return calendarRepository;
    }

    public EventNotificationStatusRegister getNoficationStatusRegister(Context context) {
        if (notificationStatusRegister == null) {
            notificationStatusRegister = new SharedPreferenceNotificationStatusRegister(context);
        }
        return notificationStatusRegister;
    }

    public NotificationDisplayVM getNotificationDisplayVM(Context context) {
        if (notificationDisplayVM == null) {
            notificationDisplayVM = new NotificationDisplayVM(context, getCalendarRepository(context), getNoficationStatusRegister(context));
        }
        return notificationDisplayVM;
    }

    public NotificationDisplayController getNotificationDisplayController(Context context) {
        if (notificationDisplayController == null) {
            notificationDisplayController= new NotificationDisplayController(context, getNotificationDisplayVM(context));
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
