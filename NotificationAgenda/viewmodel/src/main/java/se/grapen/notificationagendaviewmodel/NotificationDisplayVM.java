package se.grapen.notificationagendaviewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import se.grapen.notificationagendacalendar.CalendarEvent;
import se.grapen.notificationagendacalendar.CalendarRepository;
import se.grapen.notificationagendamodel.AppConfig;
import se.grapen.notificationagendamodel.EventNotificationStatusRegister;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationDisplayVM {

    private Context androidContext;
    private CalendarRepository calendarRepository;
    private EventNotificationStatusRegister notificationStatusRegister;
    private AppConfig config;

    public NotificationDisplayVM(Context androidContext, CalendarRepository calendarRepository, EventNotificationStatusRegister notificationStatusRegister, AppConfig config) {
        this.androidContext = androidContext;
        this.calendarRepository = calendarRepository;
        this.notificationStatusRegister = notificationStatusRegister;

        this.config = config;
    }

    public List<EventNotificationVM> loadEvents() {
        long startTimestamp = getStartTimeFromConfig();
        Set<Long> calendarIds = config.calendarsToUseIDs();
        List<CalendarEvent> calendarEvents = calendarRepository.findAllEvents(startTimestamp, calendarIds);
        sortEventWithLatestLast(calendarEvents);
        List<EventNotificationVM> events = filterDismissedEvents(calendarEvents);
        return events;
    }

    private long getStartTimeFromConfig() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(java.util.Calendar.HOUR_OF_DAY, config.runCalenderCheckAtHour());
        calendar.set(java.util.Calendar.MINUTE, config.runCalenderCheckAtMin());
        return calendar.getTimeInMillis();
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
