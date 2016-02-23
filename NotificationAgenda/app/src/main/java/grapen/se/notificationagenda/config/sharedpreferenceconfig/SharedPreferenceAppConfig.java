package grapen.se.notificationagenda.config.sharedpreferenceconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import grapen.se.notificationagenda.R;
import grapen.se.notificationagenda.config.AppConfig;

/**
 * Created by ola on 19/02/16.
 */
public class SharedPreferenceAppConfig implements AppConfig {


    private Context androidContext;

    public SharedPreferenceAppConfig(Context androidContext) {
        this.androidContext = androidContext;
    }

    @Override
    public int runCalenderCheckAtHour() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(androidContext);
        return settings.getInt(androidContext.getString(R.string.config_check_calendar_hour_key), 3);
    }

    @Override
    public int runCalenderCheckAtMin() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(androidContext);
        return settings.getInt(androidContext.getString(R.string.config_check_calendar_min_key), 0);
    }

    @Override
    public List<Long> calendarsToUseIDs() {
        String key = androidContext.getString(R.string.config_calendars_to_use_key);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(androidContext);
        Set<String> calendarIdsAsString = settings.getStringSet(key, null);
        if (calendarIdsAsString == null || calendarIdsAsString.isEmpty()) {
            return null;
        }

        List<Long> calendarIds = new ArrayList<Long>();
        for (String calIdStr : calendarIdsAsString) {
            calendarIds.add(Long.parseLong(calIdStr));
        }

        return calendarIds;
    }
}
