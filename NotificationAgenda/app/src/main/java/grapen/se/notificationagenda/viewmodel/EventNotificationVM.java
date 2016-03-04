package grapen.se.notificationagenda.viewmodel;

import android.content.Context;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import grapen.se.notificationagenda.R;

/**
 * Created by ola on 04/03/16.
 */
public class EventNotificationVM {
    private int id;
    private String title;
    private String displayTime;

    public EventNotificationVM(int id, String title, long startTs, Context androidContext) {
        this.id = id;
        this.title = title;
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(androidContext);
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(androidContext);
        this.displayTime = formatDateString(startTs, timeFormat, dateFormat, androidContext);
    }

    private String formatDateString(long startTs, DateFormat timeFormat, DateFormat dateFormat, Context androidContext) {
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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDisplayTime() {
        return displayTime;
    }
}
