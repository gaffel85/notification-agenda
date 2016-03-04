package grapen.se.notificationagenda.receivers;

import android.content.Context;
import android.content.Intent;

import grapen.se.notificationagenda.appcontext.AppContextBroadcastReceiver;
import se.grapen.notificationagendamodel.AppConfig;

/**
 * Created by ola on 17/02/16.
 */
public class BootReceiver extends AppContextBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppConfig config = getAppContext().getAppConfig(context);
        getAppContext().getScheduler(context).scheduleTimer(context, TimerReceiver.class);
    }
}
