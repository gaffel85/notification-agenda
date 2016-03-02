package grapen.se.notificationagenda.calendar;

import java.text.DateFormat;

/**
 * Created by ola on 16/02/16.
 */
public interface CalendarEvent {
    String getDisplayName();

    CharSequence getStartDateFormatted(FormatDateStringProvider formatDateStringProvider, int hoursToDisplay);

    long getId();

    interface FormatDateStringProvider {
        String getTomorrowString();
        DateFormat getTimeFormat();
        DateFormat getDateFormat();
    }
}
