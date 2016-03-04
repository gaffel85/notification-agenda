package grapen.se.notificationagenda.appcontext;

import android.content.Context;

import grapen.se.notificationagenda.controller.NotificationAgendaController;
import grapen.se.notificationagenda.scheduler.Scheduler;
import grapen.se.notificationagenda.model.CalendarRepository;
import grapen.se.notificationagenda.model.implementation.AndroidCalendarRepository;
import grapen.se.notificationagenda.model.AppConfig;
import grapen.se.notificationagenda.model.implementation.SharedPreferenceAppConfig;
import grapen.se.notificationagenda.view.NotificationDisplay;
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
    private NotificationAgendaController notificationAgendaController;
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

    public NotificationAgendaController getNotificationAgendaController(Context context) {
        if (notificationAgendaController == null) {
            notificationAgendaController = new NotificationAgendaController(context, getCalendarRepository(context), getNoficationStatusRegister(context));
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
