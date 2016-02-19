package grapen.se.notificationagenda.config.sharedpreferenceconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
