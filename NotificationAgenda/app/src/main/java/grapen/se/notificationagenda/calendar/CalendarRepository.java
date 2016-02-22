package grapen.se.notificationagenda.calendar;

import android.content.ContentResolver;

import java.util.ArrayList;

import grapen.se.notificationagenda.calendar.androidcalendar.AndroidCalendarEvent;

/**
 * Created by ola on 16/02/16.
 */
public interface CalendarRepository {
    ArrayList<CalendarEvent> findAllEvents();

    ArrayList<Calendar> findVisibleCalendars();

    ArrayList<Calendar> findAllCalendars();
}
