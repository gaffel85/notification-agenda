package grapen.se.notificationagenda;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import java.util.Calendar;

import grapen.se.notificationagenda.appcontext.AppContext;
import grapen.se.notificationagenda.appcontext.AppContextBroadcastReceiver;
import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;
import grapen.se.notificationagenda.notificationstatus.NotificationStatusRegister;

/**
 * Created by ola on 17/02/16.
 */
public class TimerManager extends AppContextBroadcastReceiver {

    private static final String WAKE_LOCK_KEY = "TimerManagerWakeLock";

    public static void scheduleTimer(Context context) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 3); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, TimerManager.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        PowerManager.WakeLock wakeLock = takeWakeLock(context);

        AppContext appContext = getAppContext();
        CalendarRepository calendarRepository = appContext.getCalendarRepository(context);
        NotificationProducer notificationProducer = appContext.getNotificationProducer(context);
        NotificationStatusRegister notificationStatusRegister = appContext.getNoficationStatusRegister(context);

        NotificationAgendaController controller = new NotificationAgendaController(calendarRepository, notificationProducer, notificationStatusRegister);
        controller.sendAgendaAsNotification();
        releaseWakeLock(wakeLock);
    }

    private void releaseWakeLock(PowerManager.WakeLock wakeLock) {
        wakeLock.release();
    }

    private PowerManager.WakeLock takeWakeLock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_KEY);
        wl.acquire();
        return wl;
    }
}
