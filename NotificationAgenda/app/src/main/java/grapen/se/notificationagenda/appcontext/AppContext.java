package grapen.se.notificationagenda.appcontext;

import android.content.Context;

import grapen.se.notificationagenda.controller.NotificationAgendaController;
import grapen.se.notificationagenda.scheduler.Scheduler;
import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.calendar.androidcalendar.AndroidCalendarRepository;
import grapen.se.notificationagenda.config.AppConfig;
import grapen.se.notificationagenda.config.sharedpreferenceconfig.SharedPreferenceAppConfig;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;
import grapen.se.notificationagenda.notificationproducer.androidnotification.AndroidNotificationProducer;
import grapen.se.notificationagenda.notificationstatus.NotificationStatusRegister;
import grapen.se.notificationagenda.notificationstatus.sharedpreference.SharedPreferenceNotificationStatusRegister;

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
    private NotificationStatusRegister notificationStatusRegister;
    private NotificationAgendaController notificationAgendaController;
    private AppConfig appConfig;
    private Scheduler scheduler;

    public CalendarRepository getCalendarRepository(Context context) {
        if (calendarRepository == null) {
            calendarRepository = new AndroidCalendarRepository(context.getContentResolver(), getAppConfig(context));
        }
        return calendarRepository;
    }

    public NotificationProducer getNotificationProducer(Context context) {
        if (notificationProducer == null) {
            notificationProducer = new AndroidNotificationProducer(context);
        }
        return notificationProducer;
    }

    public NotificationStatusRegister getNoficationStatusRegister(Context context) {
        if (notificationStatusRegister == null) {
            notificationStatusRegister = new SharedPreferenceNotificationStatusRegister(context);
        }
        return notificationStatusRegister;
    }

    public NotificationAgendaController getNotificationAgendaController(Context context) {
        if (notificationAgendaController == null) {
            notificationAgendaController = new NotificationAgendaController(getCalendarRepository(context), getNotificationProducer(context), getNoficationStatusRegister(context));
        }
        return notificationAgendaController;
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
