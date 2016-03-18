package se.grapen.notificationagendacalendar.dataaccess;

/**
 * Created by ola on 11/03/16.
 */
public interface CalendarDO {
    long getCalendarID();

    boolean isVisible();

    String getName();
}
