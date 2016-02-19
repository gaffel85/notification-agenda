package grapen.se.notificationagenda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import grapen.se.notificationagenda.appcontext.AppContextBroadcastReceiver;
import grapen.se.notificationagenda.config.AppConfig;

/**
 * Created by ola on 17/02/16.
 */
public class BootReceiver extends AppContextBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppConfig config = getAppContext().getAppConfig(context);
        TimerManager.scheduleTimer(context, config);
    }
}
