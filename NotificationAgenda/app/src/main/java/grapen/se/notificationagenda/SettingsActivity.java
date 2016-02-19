package grapen.se.notificationagenda;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import grapen.se.notificationagenda.appcontext.AppContext;
import grapen.se.notificationagenda.appcontext.AppContextActivity;
import grapen.se.notificationagenda.appcontext.AppContextPreferenceActivity;
import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;
import grapen.se.notificationagenda.notificationstatus.NotificationStatusRegister;

public class SettingsActivity extends AppContextActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //NotificationAgendaController controller = getAppContext().getNotificationAgendaController(this);
        //controller.sendAgendaAsNotification();

        TimerManager.scheduleTimer(this, getAppContext().getAppConfig(this));
    }
}
