package se.grapen.notificationagendacalendar.dataaccess;

/**
 * Created by ola on 11/03/16.
 */
public interface CalendarEventDO {
    long getCalendarId();

    String getEventTitle();

    boolean isAllDay();

    long getStartDT();

    long getEndDT();

    String getRrule();

    String getRdate();

    String getExrule();

    String getExdate();

    long getEventID();
}
