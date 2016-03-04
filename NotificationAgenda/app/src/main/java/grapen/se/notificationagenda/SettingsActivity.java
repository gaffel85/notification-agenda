package grapen.se.notificationagenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;

import grapen.se.notificationagenda.appcontext.AppContextActivity;
import grapen.se.notificationagenda.viewmodel.NotificationDisplayVM;
import grapen.se.notificationagenda.receivers.TimerReceiver;

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

        NotificationDisplayController controller = getAppContext().getNotificationDisplayController(this);
        controller.displayEvents();

        getAppContext().getScheduler(this).scheduleTimer(this, TimerReceiver.class);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> allEntries = settings.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

    }
}
