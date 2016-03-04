package grapen.se.notificationagenda;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import grapen.se.notificationagenda.receivers.DismissNotificationReceiver;
import grapen.se.notificationagenda.viewmodel.EventNotificationVM;
import grapen.se.notificationagenda.viewmodel.NotificationDisplayVM;
import se.grapen.notificationagendaview.NotificationDisplay;

/**
 * Created by ola on 04/03/16.
 */
public class NotificationDisplayController {

    private NotificationDisplay notificationDisplay;
    private List<EventNotificationVM> events = new ArrayList<EventNotificationVM>();
    private NotificationDisplayVM notificationDisplayVM;

    public NotificationDisplayController(Context androidContext, NotificationDisplayVM notificationDisplayVM) {
        this.notificationDisplayVM = notificationDisplayVM;
        this.notificationDisplay = new NotificationDisplay(androidContext);
    }

    public void displayEvents() {
        events = notificationDisplayVM.loadEvents();
        notificationDisplay.displayEventNotifications(new NotificationDisplay.Adapter() {

            @Override
            public int nbrOfEvents() {
                return events.size();
            }

            @Override
            public int getIdForEvent(int eventIndex) {
                return (int) events.get(eventIndex).getId();
            }

            @Override
            public String getTitleForEvent(int eventIndex) {
                return events.get(eventIndex).getTitle();
            }

            @Override
            public String getDisplayTimeForEvent(int eventIndex) {
                return events.get(eventIndex).getDisplayTime();
            }

            @Override
            public Class<?> getDismissIntentReceiverForEvent(int eventIndex) {
                return DismissNotificationReceiver.class;
            }
        });
    }
}
