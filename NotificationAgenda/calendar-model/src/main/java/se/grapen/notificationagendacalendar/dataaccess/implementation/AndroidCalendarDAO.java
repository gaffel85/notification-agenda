package se.grapen.notificationagendacalendar.dataaccess.implementation;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

import se.grapen.notificationagendacalendar.Calendar;
import se.grapen.notificationagendacalendar.dataaccess.CalendarDAO;
import se.grapen.notificationagendacalendar.dataaccess.CalendarDO;
import se.grapen.notificationagendacalendar.implementation.AndroidCalendar;

/**
 * Created by ola on 18/03/16.
 */
public class AndroidCalendarDAO implements CalendarDAO {

    public static final String[] CALENDAR_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.VISIBLE,                          //1
            CalendarContract.Calendars.NAME                          //1
    };

    private static final int CALENDAR_PROJECTION_ID_INDEX = 0;
    private static final int CALENDAR_PROJECTION_VISIBLE_INDEX = 1;
    private static final int CALENDAR_PROJECTION_NAME_INDEX = 2;

    private ContentResolver contentResolver;

    public AndroidCalendarDAO(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<CalendarDO> findCalendars() {
        List<CalendarDO> calendars = new ArrayList<CalendarDO>();
        Cursor calendarCursor = null;
        Uri calendarURI = CalendarContract.Calendars.CONTENT_URI;
        calendarCursor = contentResolver.query(calendarURI, CALENDAR_PROJECTION, null, null, null);

        while (calendarCursor.moveToNext()) {
            long calID = calendarCursor.getLong(CALENDAR_PROJECTION_ID_INDEX);
            boolean visible = calendarCursor.getInt(CALENDAR_PROJECTION_VISIBLE_INDEX) == 1;
            String name = calendarCursor.getString(CALENDAR_PROJECTION_NAME_INDEX);

            calendars.add(new AndroidCalendarDO(calID, visible, name));
        }
        return calendars;
    }
}
