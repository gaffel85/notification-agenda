package grapen.se.notificationagenda.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import grapen.se.notificationagenda.config.AppConfig;
import grapen.se.notificationagenda.receivers.Alarm;
import grapen.se.notificationagenda.receivers.TimerReceiver;

/**
 * Created by ola on 19/02/16.
 */
public class Scheduler {

    private AppConfig config;

    public Scheduler(AppConfig appConfig) {
        this.config = appConfig;
    }

    public void scheduleTimer(Context context, Class receiver) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, config.runCalenderCheckAtHour()); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, config.runCalenderCheckAtMin());
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, receiver);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }
}
