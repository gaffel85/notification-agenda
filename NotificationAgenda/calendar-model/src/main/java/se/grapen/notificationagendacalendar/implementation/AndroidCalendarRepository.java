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

/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendarRepository implements CalendarRepository {
    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] CALENDAR_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.VISIBLE,                          //1
            CalendarContract.Calendars.NAME                          //1
    };

    // The indices for the projection array above.
    private static final int CALENDAR_PROJECTION_ID_INDEX = 0;
    private static final int CALENDAR_PROJECTION_VISIBLE_INDEX = 1;
    private static final int CALENDAR_PROJECTION_NAME_INDEX = 2;

    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Events._ID,                           // 0
            CalendarContract.Events.TITLE,                          //1
            CalendarContract.Events.DTSTART,                          //2
            CalendarContract.Events.DTEND,                          //3
            CalendarContract.Events.CALENDAR_ID,                          //3
            CalendarContract.Events.ALL_DAY,
            CalendarContract.Events.RRULE,
            CalendarContract.Events.RDATE,
            CalendarContract.Events.EXDATE,
            CalendarContract.Events.EXRULE,
            CalendarContract.Events.DURATION

    };

    // The indices for the projection array above.
    private static final int EVENT_PROJECTION_ID_INDEX = 0;
    private static final int EVENT_PROJECTION_TITLE_INDEX = 1;
    private static final int EVENT_PROJECTION_DTSTART_INDEX = 2;
    private static final int EVENT_PROJECTION_DTEND_INDEX = 3;
    private static final int EVENT_PROJECTION_CALENDAR_ID_INDEX = 4;
    private static final int EVENT_PROJECTION_ALl_DAY_INDEX = 5;
    private static final int EVENT_PROJECTION_RRULE_INDEX = 6;
    private static final int EVENT_PROJECTION_RDATE_INDEX = 7;
    private static final int EVENT_PROJECTION_EXDATE_INDEX = 8;
    private static final int EVENT_PROJECTION_EXRULE_INDEX = 9;
    private static final int EVENT_PROJECTION_DURATION_INDEX = 10;

    private ContentResolver contentResolver;

    public AndroidCalendarRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public ArrayList<CalendarEvent> findAllEvents(long startTimestamp, Set<Long> calendarIds) {
        List<Calendar> calendars = findAllCalendars(calendarIds);
        ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

        java.util.Calendar startTime = java.util.Calendar.getInstance();
        startTime.setTimeInMillis(startTimestamp);
        long startTs = startTime.getTimeInMillis();
        startTime.add(java.util.Calendar.HOUR_OF_DAY, 24);
        long endTs = startTime.getTimeInMillis();

        for (Calendar calendar : calendars) {
            if (calendar.isSelected()) {
                events.addAll(fetchEventsForCalendar(calendar.getId(), contentResolver, startTs, endTs));
            }
        }
        return events;
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

    @Override
    public ArrayList<Calendar> findVisibleCalendars() {
        ArrayList<Calendar> visibleCalendars = new ArrayList<Calendar>();
        ArrayList<Calendar> calendars = findRawCalendars();
        for (Calendar calendar : calendars) {
            if (calendar.isVisible()) {
                visibleCalendars.add(calendar);
            }
        }
        return visibleCalendars;
    }

    public ArrayList<Calendar> findRawCalendars() {
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        Cursor calendarCursor = null;
        Uri calendarURI = CalendarContract.Calendars.CONTENT_URI;
        calendarCursor = contentResolver.query(calendarURI, CALENDAR_PROJECTION, null, null, null);

        while (calendarCursor.moveToNext()) {
            long calID = calendarCursor.getLong(CALENDAR_PROJECTION_ID_INDEX);
            boolean visible = calendarCursor.getInt(CALENDAR_PROJECTION_VISIBLE_INDEX) == 1;
            String name = calendarCursor.getString(CALENDAR_PROJECTION_NAME_INDEX);

            calendars.add(new AndroidCalendar(calID, visible, name));
        }
        return calendars;
    }

    private ArrayList<CalendarEvent> fetchEventsForCalendar(Long calID, ContentResolver cr, long startTs, long endTs) {
        ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

        Cursor eventCursor = null;
        String eventSelection = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] eventSelectionArgs = new String[] {calID.toString()};

        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder, startTs);
        ContentUris.appendId(eventsUriBuilder, endTs);
        Uri eventsUri = eventsUriBuilder.build();

        eventCursor = cr.query(eventsUri, EVENT_PROJECTION, eventSelection, eventSelectionArgs, CalendarContract.Instances.DTSTART + " ASC");

        while (eventCursor.moveToNext()) {
            long eventID = 0;
            String eventTitle = null;

            // Get the field values
            eventID = eventCursor.getLong(EVENT_PROJECTION_ID_INDEX);
            eventTitle = eventCursor.getString(EVENT_PROJECTION_TITLE_INDEX);
            long calendarId = eventCursor.getLong(EVENT_PROJECTION_CALENDAR_ID_INDEX);
            long startDT = eventCursor.getLong(EVENT_PROJECTION_DTSTART_INDEX);
            long endDT = eventCursor.getLong(EVENT_PROJECTION_DTEND_INDEX);
            boolean allDay = eventCursor.getInt(EVENT_PROJECTION_ALl_DAY_INDEX) == 1;

            String rrule = eventCursor.getString(EVENT_PROJECTION_RRULE_INDEX);
            String rdate = eventCursor.getString(EVENT_PROJECTION_RDATE_INDEX);
            String exrule = eventCursor.getString(EVENT_PROJECTION_EXRULE_INDEX);
            String exdate = eventCursor.getString(EVENT_PROJECTION_EXDATE_INDEX);

            AndroidCalendarEvent event = new AndroidCalendarEvent(eventID, calendarId, eventTitle, allDay, startDT, endDT, rrule, rdate, exrule, exdate);
            boolean isAllDayEventStartingTomorrow = event.isOnNextDay() && event.isAllDay();
            if (!isAllDayEventStartingTomorrow) {
                events.add(event);
            }
        }
        return events;
    }
}
