package grapen.se.notificationagenda;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.MultiSelectListPreference;
import android.provider.CalendarContract;
import android.util.AttributeSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import grapen.se.notificationagenda.calendar.Calendar;

/**
 * Created by ola on 20/02/16.
 */
public class CalendarListPreference extends MultiSelectListPreference {

    public CalendarListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*List<CharSequence> entries = new ArrayList<CharSequence>();
        List<CharSequence> entriesValues = new ArrayList<CharSequence>();

        cr = context.getContentResolver();
        cursor = cr.query(CalendarContract.Calendars.CONTENT_URI, projection, selection, selectionArgs, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String displayName = cursor.getString(1);

            entries.add(name);
            entriesValues.add(displayName);
        }

        setEntries(entries.toArray(new CharSequence[]{}));
        setEntryValues(entriesValues.toArray(new CharSequence[]{}));*/
    }

    public void updateList(List<Calendar> calendars) {
        String[] entries = new String[calendars.size()];
        String[] values = new String[calendars.size()];
        Set<String> checkedValues = new HashSet<String>();
        int index = 0;
        for (Calendar calendar : calendars) {
            entries[index] = calendar.getName();
            String key = "USE_CALENDAR-" + calendar.getId();
            values[index] = key;
            if (calendar.isVisible()) {
                checkedValues.add(key);
            }
            index++;
        }
        setEntries(entries);
        setEntryValues(values);
        setValues(checkedValues);
    }
}
