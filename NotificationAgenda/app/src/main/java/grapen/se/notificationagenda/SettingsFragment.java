package grapen.se.notificationagenda;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import grapen.se.notificationagenda.appcontext.AppContextPreferenceFragment;
import grapen.se.notificationagenda.receivers.TimerReceiver;
import se.grapen.notificationagendamodel.AppConfig;

/**
 * Created by ola on 19/02/16.
 */
public class SettingsFragment extends AppContextPreferenceFragment implements TimePickerDialog.OnTimeSetListener {

    private Preference timePickerPref;
    private CalendarListPreference calendarListPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        timePickerPref = (Preference) findPreference("btnTimePicker");
        timePickerPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                showTimeDialog();
                return false;
            }
        });

        calendarListPref = (CalendarListPreference) findPreference(getActivity().getString(R.string.config_calendars_to_use_key));
        CalendarFetcherTask task = new CalendarFetcherTask();
        task.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateCheckTimeValue();
    }

    // TODO: 24h
    private void showTimeDialog(){
        boolean hour24 = DateFormat.is24HourFormat(getActivity());
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int hour = settings.getInt(getActivity().getString(R.string.config_check_calendar_hour_key), 3);
        int min = settings.getInt(getActivity().getString(R.string.config_check_calendar_min_key), 0);
        new TimePickerDialog(getActivity(), this, hour, min, hour24).show();
    }

    private void updateCheckTimeValue() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int hour = settings.getInt(getActivity().getString(R.string.config_check_calendar_hour_key), 3);
        int min = settings.getInt(getActivity().getString(R.string.config_check_calendar_min_key), 0);
        setCheckTimeValue(hour, min);
    }

    private void setCheckTimeValue(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
        String time = timeFormat.format(calendar.getTime());
        timePickerPref.setSummary(time);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setCheckTimeValue(hourOfDay, minute);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(getActivity().getString(R.string.config_check_calendar_hour_key), hourOfDay);
        editor.putInt(getActivity().getString(R.string.config_check_calendar_min_key), minute);
        editor.commit();

        getAppContext().getScheduler(getActivity()).scheduleTimer(getActivity(), TimerReceiver.class);
    }

    public class CalendarFetcherTask extends AsyncTask<Void, Void, List<se.grapen.notificationagendacalendar.Calendar>> {

        @Override
        protected List<se.grapen.notificationagendacalendar.Calendar> doInBackground(Void... params) {
            AppConfig config = getAppContext().getAppConfig(getActivity());
            return getAppContext().getCalendarRepository(getActivity()).findAllCalendars(config.calendarsToUseIDs());
        }

        protected void onPostExecute(List<se.grapen.notificationagendacalendar.Calendar> calendars) {
            updateCalendarList(calendars);
        }
    }

    private void updateCalendarList(List<se.grapen.notificationagendacalendar.Calendar> calendars) {
        calendarListPref.updateList(calendars);
    }
}
