package se.grapen.notificationagendacalendar.dataaccess.implementation;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

import se.grapen.notificationagendacalendar.CalendarEvent;
import se.grapen.notificationagendacalendar.dataaccess.CalendarEventDAO;
import se.grapen.notificationagendacalendar.dataaccess.CalendarEventDO;
import se.grapen.notificationagendacalendar.implementation.AndroidCalendarEvent;

/**
 * Created by ola on 18/03/16.
 */
public class AndroidCalendarEventDAO implements CalendarEventDAO {

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
            CalendarContract.Events.EXRULE

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
    private ContentResolver contentResolver;

    public AndroidCalendarEventDAO(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<CalendarEventDO> findEvents(Long calendarId, long startTimestamp, long endTimestamp) {
        List<CalendarEventDO> events = new ArrayList<CalendarEventDO>();

        Cursor eventCursor = null;
        String eventSelection = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] eventSelectionArgs = new String[] {calendarId.toString()};

        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder, startTimestamp);
        ContentUris.appendId(eventsUriBuilder, endTimestamp);
        Uri eventsUri = eventsUriBuilder.build();

        eventCursor = contentResolver.query(eventsUri, EVENT_PROJECTION, eventSelection, eventSelectionArgs, CalendarContract.Instances.DTSTART + " ASC");

        while (eventCursor.moveToNext()) {
            long eventID = 0;
            String eventTitle = null;

            // Get the field values
            eventID = eventCursor.getLong(EVENT_PROJECTION_ID_INDEX);
            eventTitle = eventCursor.getString(EVENT_PROJECTION_TITLE_INDEX);
            long calId = eventCursor.getLong(EVENT_PROJECTION_CALENDAR_ID_INDEX);
            long startDT = eventCursor.getLong(EVENT_PROJECTION_DTSTART_INDEX);
            long endDT = eventCursor.getLong(EVENT_PROJECTION_DTEND_INDEX);
            boolean allDay = eventCursor.getInt(EVENT_PROJECTION_ALl_DAY_INDEX) == 1;

            String rrule = eventCursor.getString(EVENT_PROJECTION_RRULE_INDEX);
            String rdate = eventCursor.getString(EVENT_PROJECTION_RDATE_INDEX);
            String exrule = eventCursor.getString(EVENT_PROJECTION_EXRULE_INDEX);
            String exdate = eventCursor.getString(EVENT_PROJECTION_EXDATE_INDEX);


            AndroidCalendarEventDO event = new AndroidCalendarEventDO(eventID, calId, eventTitle, allDay, startDT, endDT, rrule, rdate, exrule, exdate);
            events.add(event);
        }
        return events;
    }
}
