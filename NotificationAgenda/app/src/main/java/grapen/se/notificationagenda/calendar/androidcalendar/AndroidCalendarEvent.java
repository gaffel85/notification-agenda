package grapen.se.notificationagenda.calendar.androidcalendar;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import grapen.se.notificationagenda.calendar.CalendarEvent;

/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendarEvent implements CalendarEvent {

    private long eventID;
    private final String displayName;
    private final long startTs;
    private final long endTs;
    private long calendarID;

    public AndroidCalendarEvent(long eventID, String displayName, long startTs, long endTs, long calendarId) {
        this.eventID = eventID;
        this.displayName = displayName;
        this.startTs = startTs;
        this.endTs = endTs;
        this.calendarID = calendarId;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public CharSequence getStartDateFormatted(FormatDateStringProvider formatDateStringProvider, int hoursToDisplay) {
        Date startDate = new Date(startTs);

        if (isDateToday(startDate, hoursToDisplay)) {
            return formatDateStringProvider.getTimeFormat().format(startDate);
        } else if (isDateTomorrow(startDate, hoursToDisplay)) {
            java.text.DateFormat timeFormat = formatDateStringProvider.getTimeFormat();
            return formatDateStringProvider.getTomorrowString() + " " + timeFormat.format(startDate);
        } else {
            java.text.DateFormat dateFormat = formatDateStringProvider.getDateFormat();
            java.text.DateFormat timeFormat = formatDateStringProvider.getTimeFormat();
            String date = dateFormat.format(startDate) + " " + timeFormat.format(startDate);
            return dateFormat.format(startDate);
        }
    }

    private boolean isDateTomorrow(Date date, int hoursToDisplay) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        int dateDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int dateHour = dateCalendar.get(Calendar.HOUR_OF_DAY);

        return dateHour < hoursToDisplay;

        /*Date today = new Date();
        Calendar tomorrowCalendar = Calendar.getInstance();
        tomorrowCalendar.setTime(today);
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1);

        return tomorrowCalendar.get(Calendar.DAY_OF_MONTH) == dateDay;*/
    }

    // TODO: Add support for dates outside 24h
    private boolean isDateToday(Date date, int hoursToDisplay) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        int dateDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int dateHour = dateCalendar.get(Calendar.HOUR_OF_DAY);

        return dateHour >= hoursToDisplay;

        /*Date today = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(today);

        return todayCalendar.get(Calendar.DAY_OF_MONTH) == dateDay;*/
    }

    @Override
    public long getId() {
        return this.eventID;
    }
}
