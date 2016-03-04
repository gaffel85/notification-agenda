package grapen.se.notificationagenda.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import grapen.se.notificationagenda.NotificationDisplayController;
import grapen.se.notificationagenda.appcontext.AppContextBroadcastReceiver;

/**
 * Created by ola on 17/02/16.
 */
public class TimerReceiver extends AppContextBroadcastReceiver {

    private static final String WAKE_LOCK_KEY = "TimerManagerWakeLock";

    @Override
    public void onReceive(Context context, Intent arg1) {
        PowerManager.WakeLock wakeLock = takeWakeLock(context);
        //Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();

        NotificationDisplayController controller = getAppContext().getNotificationDisplayController(context);
        controller.displayEvents();

        releaseWakeLock(wakeLock);
    }

    private void releaseWakeLock(PowerManager.WakeLock wakeLock) {
        wakeLock.release();
    }

    private PowerManager.WakeLock takeWakeLock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_KEY);
        wl.acquire();
        return wl;
    }
}
