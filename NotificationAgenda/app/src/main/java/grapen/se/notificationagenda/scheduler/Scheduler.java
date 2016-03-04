package grapen.se.notificationagenda.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import se.grapen.notificationagendamodel.AppConfig;

/**
 * Created by ola on 19/02/16.
 */
public class Scheduler {

    private AppConfig config;

    public Scheduler(AppConfig appConfig) {
        this.config = appConfig;
    }

    public void scheduleTimer(Context context, Class receiver) {
        Calendar firingCal = Calendar.getInstance();
        firingCal.set(Calendar.HOUR_OF_DAY, config.runCalenderCheckAtHour()); // For 1 PM or 2 PM
        firingCal.set(Calendar.MINUTE, config.runCalenderCheckAtMin());
        firingCal.set(Calendar.SECOND, 0);

        if (isInPast(firingCal)) {
            firingCal.add(Calendar.HOUR_OF_DAY, 24);
        }

        cancelTimer(context, receiver);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = createPendingIntent(context, receiver);
        alarmManager.setRepeating(AlarmManager.RTC, firingCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private boolean isInPast(Calendar timeToCheck) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        return timeToCheck.getTimeInMillis() < currentTime;
    }

    private void cancelTimer(Context context, Class receiver)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createPendingIntent(context, receiver));
    }

    private PendingIntent createPendingIntent(Context context, Class receiver) {
        Intent intent = new Intent(context, receiver);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
