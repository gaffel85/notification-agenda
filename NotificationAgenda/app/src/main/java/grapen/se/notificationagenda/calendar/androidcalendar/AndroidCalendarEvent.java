package grapen.se.notificationagenda.calendar.androidcalendar;

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
        return "2016-03-17 24:00";
    }

    @Override
    public long getId() {
        return this.eventID;
    }
}
