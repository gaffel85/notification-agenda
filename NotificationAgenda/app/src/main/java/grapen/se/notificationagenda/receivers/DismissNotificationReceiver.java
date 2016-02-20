package grapen.se.notificationagenda.receivers;

import android.content.Context;
import android.content.Intent;

import grapen.se.notificationagenda.appcontext.AppContextBroadcastReceiver;
import grapen.se.notificationagenda.notificationproducer.androidnotification.EventNotification;
import grapen.se.notificationagenda.notificationstatus.NotificationStatusRegister;

/**
 * Created by ola on 19/02/16.
 */
public class DismissNotificationReceiver extends AppContextBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationStatusRegister statusRegister = getAppContext().getNoficationStatusRegister(context);
        int notificationId = intent.getIntExtra(EventNotification.NOTIFICATION_DISMISS_INTENT_EXTRA_ID, -1);
        if (notificationId >= 0) {
            statusRegister.didDismiss(notificationId);
        }
    }
}
