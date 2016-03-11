package se.grapen.notificationagendacalendar;

/**
 * Created by ola on 16/02/16.
 */
public interface CalendarEvent {
    String getDisplayName();

    long getId();

    long getStartTimestamp();

    boolean isAllDay();

    boolean isAllDayToday();
}
