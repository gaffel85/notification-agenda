package grapen.se.notificationagenda.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import grapen.se.notificationagenda.model.CalendarEvent;
import grapen.se.notificationagenda.model.CalendarRepository;
import grapen.se.notificationagenda.model.EventNotificationStatusRegister;
import grapen.se.notificationagenda.view.NotificationDisplay;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationDisplayVM {

    private Context androidContext;
    private CalendarRepository calendarRepository;
    private EventNotificationStatusRegister notificationStatusRegister;

    public NotificationDisplayVM(Context androidContext, CalendarRepository calendarRepository, EventNotificationStatusRegister notificationStatusRegister) {
        this.androidContext = androidContext;
        this.calendarRepository = calendarRepository;
        this.notificationStatusRegister = notificationStatusRegister;

    }

    public List<EventNotificationVM> loadEvents() {
        List<CalendarEvent> calendarEvents = calendarRepository.findAllEvents();
        List<EventNotificationVM> events = filterDismissedEvents(calendarEvents);
        return events;
    }

    private List<EventNotificationVM> filterDismissedEvents(List<CalendarEvent> events) {
        List<EventNotificationVM> filteredEvents = new ArrayList<EventNotificationVM>();
        for (CalendarEvent event : events) {
            if (!notificationStatusRegister.isDismissed(event.getId())) {
                filteredEvents.add(new EventNotificationVM((int) event.getId(), event.getDisplayName(), event.getStartTimestamp(), androidContext));
            }
        }
        return filteredEvents;
    }
}
