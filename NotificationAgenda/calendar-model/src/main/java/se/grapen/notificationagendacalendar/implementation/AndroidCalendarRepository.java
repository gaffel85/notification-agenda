package se.grapen.notificationagendacalendar.implementation;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import se.grapen.notificationagendacalendar.Calendar;
import se.grapen.notificationagendacalendar.CalendarEvent;
import se.grapen.notificationagendacalendar.CalendarRepository;
import se.grapen.notificationagendacalendar.dataaccess.CalendarDAO;
import se.grapen.notificationagendacalendar.dataaccess.CalendarDO;
import se.grapen.notificationagendacalendar.dataaccess.CalendarEventDAO;
import se.grapen.notificationagendacalendar.dataaccess.CalendarEventDO;
import se.grapen.notificationagendacalendar.dataaccess.implementation.AndroidCalendarDAO;
import se.grapen.notificationagendacalendar.dataaccess.implementation.AndroidCalendarEventDAO;

/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendarRepository implements CalendarRepository {

    private CalendarEventDAO eventDAO;
    private CalendarDAO calendarDAO;

    public AndroidCalendarRepository(ContentResolver contentResolver) {
        this.eventDAO = new AndroidCalendarEventDAO(contentResolver);
        this.calendarDAO = new AndroidCalendarDAO(contentResolver);
    }

    public void setCalendarEventDAO(CalendarEventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public void setCalendarDAO(CalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;
    }

    @Override
    public List<CalendarEvent> findAllEvents(long startTimestamp, Set<Long> calendarIds) {
        List<Calendar> calendars = findAllCalendars(calendarIds);
        ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

        java.util.Calendar startTime = java.util.Calendar.getInstance();
        startTime.setTimeInMillis(startTimestamp);
        long startTs = startTime.getTimeInMillis();
        startTime.add(java.util.Calendar.HOUR_OF_DAY, 24);
        long endTs = startTime.getTimeInMillis();

        for (Calendar calendar : calendars) {
            if (calendar.isSelected()) {
                events.addAll(fetchEventsForCalendar(calendar.getId(), startTs, endTs));
            }
        }
        return events;
    }

    @Override
    public List<CalendarEvent> findAllEventsFromVisibleCalendars(long startTimestamp) {
        return findAllEvents(startTimestamp, null);
    }

    @Override
    public List<Calendar> findAllCalendars(Set<Long> markedCalendarIds) {
        Set<Long> calendarsToUseIDs = markedCalendarIds;
        List<Calendar> allCalendars = findRawCalendars();
        if (calendarsToUseIDs == null) {
            markVisibleAsSelected(allCalendars);
        } else {
            markSelectedFromSet(allCalendars, calendarsToUseIDs);
        }
        return allCalendars;
    }

    private void markVisibleAsSelected(List<Calendar> rawCalendars) {
        for (Calendar calendar : rawCalendars) {
            if (calendar.isVisible()) {
                calendar.setSelected();
            }
        }
    }

    private void markSelectedFromSet(List<Calendar> allCalendars, Set<Long> calendarIds) {
        for (Calendar calendar : allCalendars) {
            if (calendarIds.contains(calendar.getId())) {
                calendar.setSelected();
            }
        }
    }

    public ArrayList<Calendar> findRawCalendars() {
        List<CalendarDO> calendarDOs = calendarDAO.findCalendars();
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();

        for (CalendarDO calendarDO : calendarDOs) {
            calendars.add(new AndroidCalendar(calendarDO));
        }
        return calendars;
    }

    private List<CalendarEvent> fetchEventsForCalendar(Long calID, long startTs, long endTs) {
        List<CalendarEventDO> eventsDO = eventDAO.findEvents(calID, startTs, endTs);
        List<CalendarEvent> events = new ArrayList<CalendarEvent>();

        for (CalendarEventDO eventDO : eventsDO) {


            AndroidCalendarEvent event = new AndroidCalendarEvent(eventDO);
            boolean isAllDayEventStartingTomorrow = event.isOnNextDay() && event.isAllDay();
            if (!isAllDayEventStartingTomorrow) {
                events.add(event);
            }
        }
        return events;
    }
}
