package se.grapen.notificationagendaview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationDisplay {

    //TODO: Shuould this be here?
    public static final String NOTIFICATION_DISMISS_INTENT_EXTRA_ID = "NOTIFICATION_DISMISS_INTENT_EXTRA_ID";

    public interface Adapter {
        int nbrOfEvents();
        int getIdForEvent(int eventIndex);
        String getTitleForEvent(int eventIndex);
        String getDisplayTimeForEvent(int eventIndex);
        Class<?> getDismissIntentReceiverForEvent(int eventIndex);
    }

    private Context androidContext;

    public NotificationDisplay(Context androidContext) {
        this.androidContext = androidContext;
    }

    public void displayEventNotifications(Adapter adapter) {
        NotificationManager notificationManager = (NotificationManager) androidContext.getSystemService(androidContext.NOTIFICATION_SERVICE);

        if (adapter.nbrOfEvents() == 0) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
            builder.setContentTitle("No Events");
            builder.setSmallIcon(R.drawable.notification_icon);
            notificationManager.notify(99999999, builder.build());

            return;
        } else {
            for (int i = 0; i < adapter.nbrOfEvents(); i++) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(androidContext);
                builder.setContentTitle(adapter.getTitleForEvent(i));
                builder.setContentText(adapter.getDisplayTimeForEvent(i));
                builder.setSmallIcon(R.drawable.notification_icon);

                int intentId = adapter.getIdForEvent(i);
                Intent intent = new Intent(androidContext, adapter.getDismissIntentReceiverForEvent(i));
                intent.putExtra(NOTIFICATION_DISMISS_INTENT_EXTRA_ID, intentId);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(androidContext, intentId, intent, 0);
                builder.setDeleteIntent(pendingIntent);

                Notification notification = builder.build();
                notificationManager.notify(intentId, notification);
            }
        }
    }
}
