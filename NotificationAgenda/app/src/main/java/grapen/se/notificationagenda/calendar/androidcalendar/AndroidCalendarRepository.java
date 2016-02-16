package grapen.se.notificationagenda.calendar.androidcalendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;

import grapen.se.notificationagenda.calendar.Calendar;
import grapen.se.notificationagenda.calendar.CalendarEvent;
import grapen.se.notificationagenda.calendar.CalendarRepository;

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
    };

    // The indices for the projection array above.
    private static final int EVENT_PROJECTION_ID_INDEX = 0;
    private static final int EVENT_PROJECTION_TITLE_INDEX = 1;
    private static final int EVENT_PROJECTION_DTSTART_INDEX = 2;
    private static final int EVENT_PROJECTION_DTEND_INDEX = 3;
    private static final int EVENT_PROJECTION_CALENDAR_ID_INDEX = 4;

    private ContentResolver contentResolver;

    public AndroidCalendarRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public ArrayList<CalendarEvent> findAllEvents() {
        ArrayList<Calendar> calendars = findAllCalendars();
        ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
        long now = System.currentTimeMillis();
        long tomorrow = now + 24*60*60*1000;
        for (Calendar calendar : calendars) {
            events.addAll(fetchEventsForCalendar(calendar.getId(), contentResolver, now, tomorrow));
        }
        return events;
    }

    @Override
    public ArrayList<Calendar> findAllCalendars() {
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        Cursor calendarCursor = null;
        Uri calendarURI = CalendarContract.Calendars.CONTENT_URI;
        calendarCursor = contentResolver.query(calendarURI, CALENDAR_PROJECTION, null, null, null);

        while (calendarCursor.moveToNext()) {
            long calID = 0;

            // Get the field values
            calID = calendarCursor.getLong(CALENDAR_PROJECTION_ID_INDEX);
            boolean visible = calendarCursor.getInt(CALENDAR_PROJECTION_VISIBLE_INDEX) == 1;
            String name = calendarCursor.getString(CALENDAR_PROJECTION_NAME_INDEX);

            if (visible) {
                calendars.add(new AndroidCalendar(calID));
            }
        }
        return calendars;
    }

    private ArrayList<CalendarEvent> fetchEventsForCalendar(Long calID, ContentResolver cr, long startTs, long endTs) {
        ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

        Cursor eventCursor = null;
        /*Uri eventURI = CalendarContract.Events.CONTENT_URI;*/
       /* String eventSelection = CalendarContract.Events.CALENDAR_ID + " = ? AND "((" + CalendarContract.Events.DTSTART + " >= ?) AND ("
                + CalendarContract.Events.DTSTART + " <= ?))";*/

        String eventSelection = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] eventSelectionArgs = new String[] {calID.toString()};

        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder, startTs);
        ContentUris.appendId(eventsUriBuilder, endTs);
        Uri eventsUri = eventsUriBuilder.build();

        /*String[] eventSelectionArgs = new String[] {((Long) calID).toString(), ((Long) 1455652474169l).toString(), ((Long) endTs).toString()};*/
        eventCursor = cr.query(eventsUri, EVENT_PROJECTION, eventSelection, eventSelectionArgs, CalendarContract.Instances.DTSTART + " ASC");

        while (eventCursor.moveToNext()) {
            long eventID = 0;
            String eventTitle = null;
            long eventStartTs = 0;
            long eventEndTs = 0;

            // Get the field values
            eventID = eventCursor.getLong(EVENT_PROJECTION_ID_INDEX);
            eventTitle = eventCursor.getString(EVENT_PROJECTION_TITLE_INDEX);
            long calendarId = eventCursor.getLong(EVENT_PROJECTION_CALENDAR_ID_INDEX);
            long startDT = eventCursor.getLong(EVENT_PROJECTION_DTSTART_INDEX);
            long endDT = eventCursor.getLong(EVENT_PROJECTION_DTEND_INDEX);

            events.add(new AndroidCalendarEvent(eventID, eventTitle, startDT,endDT, calendarId));
        }
        return events;
    }
}
