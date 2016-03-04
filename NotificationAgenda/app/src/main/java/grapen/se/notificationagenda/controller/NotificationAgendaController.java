package grapen.se.notificationagenda.controller;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import grapen.se.notificationagenda.model.CalendarEvent;
import grapen.se.notificationagenda.model.CalendarRepository;
import grapen.se.notificationagenda.view.EventNotification;
import grapen.se.notificationagenda.model.EventNotificationStatusRegister;
import grapen.se.notificationagenda.view.NotificationDisplay;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationAgendaController {

    private CalendarRepository calendarRepository;
    private NotificationDisplay notificationDisplay;
    private EventNotificationStatusRegister notificationStatusRegister;
    private List<CalendarEvent> events = new ArrayList<CalendarEvent>();

    public NotificationAgendaController(Context androidContext, CalendarRepository calendarRepository, EventNotificationStatusRegister notificationStatusRegister) {
        this.calendarRepository = calendarRepository;
        this.notificationStatusRegister = notificationStatusRegister;

        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(androidContext);
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(androidContext);
        this.notificationDisplay = new NotificationDisplay(androidContext, timeFormat, dateFormat, new NotificationDisplay.Adapter() {

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
                return events.get(eventIndex).getDisplayName();
            }

            @Override
            public long getStartTimestampForEvent(int eventIndex) {
                return events.get(eventIndex).getStartTimestamp();
            }
        });
    }

    public void sendAgendaAsNotification() {
        List<CalendarEvent> calendarEvents = calendarRepository.findAllEvents();
        events = filterDismissedEvents(calendarEvents);
        notificationDisplay.displayEventNotifications();
    }

    private List<CalendarEvent> filterDismissedEvents(List<CalendarEvent> events) {
        List<CalendarEvent> filteredEvents = new ArrayList<CalendarEvent>();
        for (CalendarEvent event : events) {
            if (!notificationStatusRegister.isDismissed(event.getId())) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}
