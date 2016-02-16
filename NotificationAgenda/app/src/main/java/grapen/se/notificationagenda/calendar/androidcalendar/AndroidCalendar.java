package grapen.se.notificationagenda.calendar.androidcalendar;

import android.content.ContentResolver;

import java.util.List;

import grapen.se.notificationagenda.calendar.Calendar;

/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendar implements Calendar {
    private long id;

    public AndroidCalendar(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    public List<AndroidCalendarEvent> fetchEvents(ContentResolver contentResolver) {
        /*Cursor cursor = contentResolver.query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < CNames.length; i++) {

            nameOfEvent.add(cursor.getString(1));
            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            descriptions.add(cursor.getString(2));
            CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
        return nameOfEvent;*/
        return null;
    }
}
