package grapen.se.notificationagenda;

import android.os.Bundle;

import grapen.se.notificationagenda.appcontext.AppContextActivity;
import grapen.se.notificationagenda.controller.NotificationAgendaController;
import grapen.se.notificationagenda.receivers.Alarm;
import grapen.se.notificationagenda.receivers.TimerReceiver;
import grapen.se.notificationagenda.scheduler.Scheduler;

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

        NotificationAgendaController controller = getAppContext().getNotificationAgendaController(this);
        //controller.sendAgendaAsNotification();

        getAppContext().getScheduler(this).scheduleTimer(this, TimerReceiver.class);
    }
}
