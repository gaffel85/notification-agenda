package se.grapen.notificationagendaviewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import se.grapen.notificationagendamodel.CalendarEvent;
import se.grapen.notificationagendamodel.CalendarRepository;
import se.grapen.notificationagendamodel.EventNotificationStatusRegister;

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
        sortEventWithLatestLast(calendarEvents);
        List<EventNotificationVM> events = filterDismissedEvents(calendarEvents);
        return events;
    }

    private void sortEventWithLatestLast(List<CalendarEvent> events) {
        Arrays.sort(events.toArray(new CalendarEvent[0]), new Comparator<CalendarEvent>() {

            @Override
            public int compare(CalendarEvent lhs, CalendarEvent rhs) {
                return (int) (lhs.getStartTimestamp() - rhs.getStartTimestamp());
            }
        });
    }

    private List<EventNotificationVM> filterDismissedEvents(List<CalendarEvent> events) {
        List<EventNotificationVM> filteredEvents = new ArrayList<EventNotificationVM>();
        for (CalendarEvent event : events) {
            if (!notificationStatusRegister.isDismissed(event.getId())) {
                boolean allDay = event.isAllDay() || event.isAllDayToday();
                filteredEvents.add(new EventNotificationVM((int) event.getId(), event.getDisplayName(), event.getStartTimestamp(), allDay, androidContext));
            }
        }
        return filteredEvents;
    }
}
