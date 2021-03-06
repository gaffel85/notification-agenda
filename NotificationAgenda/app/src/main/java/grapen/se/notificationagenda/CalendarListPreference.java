package grapen.se.notificationagenda;

import android.content.Context;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.grapen.notificationagendacalendar.Calendar;


/**
 * Created by ola on 20/02/16.
 */
public class CalendarListPreference extends MultiSelectListPreference {

    public CalendarListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateList(List<Calendar> calendars) {
        String[] entries = new String[calendars.size()];
        String[] values = new String[calendars.size()];
        Set<String> checkedValues = new HashSet<String>();
        int index = 0;
        for (Calendar calendar : calendars) {
            entries[index] = calendar.getName();
            String key = ((Long) calendar.getId()).toString();
            values[index] = key;
            if (calendar.isSelected()) {
                checkedValues.add(key);
            }
            index++;
        }
        setEntries(entries);
        setEntryValues(values);
        setValues(checkedValues);
    }
}
