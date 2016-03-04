package grapen.se.notificationagenda.model;

import java.text.DateFormat;

/**
 * Created by ola on 16/02/16.
 */
public interface CalendarEvent {
    String getDisplayName();

    long getId();

    long getStartTimestamp();
}
