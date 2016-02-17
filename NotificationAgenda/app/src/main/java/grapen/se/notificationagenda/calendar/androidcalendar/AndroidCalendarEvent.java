package grapen.se.notificationagenda.calendar.androidcalendar;

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
    public CharSequence getStartDateFormatted() {
        Date startDate = new Date(startTs);
        
        //TODO: Settings, date format
        if (isDateToday(startDate)) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            return timeFormat.format(startDate);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return dateFormat.format(startDate);
        }
    }

    private boolean isDateToday(Date date) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        int dateDay = dateCalendar.get(Calendar.DAY_OF_MONTH);

        Date today = new Date(startTs);
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(today);

        return todayCalendar.get(Calendar.DAY_OF_MONTH) == dateDay;
    }

    @Override
    public long getId() {
        return this.eventID;
    }
}
