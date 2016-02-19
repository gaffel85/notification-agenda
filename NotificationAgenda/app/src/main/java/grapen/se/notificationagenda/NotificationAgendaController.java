package grapen.se.notificationagenda;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import grapen.se.notificationagenda.calendar.CalendarEvent;
import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;
import grapen.se.notificationagenda.notificationproducer.androidnotification.EventNotification;
import grapen.se.notificationagenda.notificationstatus.NotificationStatusRegister;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationAgendaController {

    private CalendarRepository calendarRepository;
    private NotificationProducer notificationProducer;
    private NotificationStatusRegister notificationStatusRegister;

    public NotificationAgendaController(CalendarRepository calendarRepository, NotificationProducer notificationProducer, NotificationStatusRegister notificationStatusRegister) {
        this.calendarRepository = calendarRepository;
        this.notificationProducer = notificationProducer;
        this.notificationStatusRegister = notificationStatusRegister;
    }

    public void sendAgendaAsNotification() {
        List<CalendarEvent> events = calendarRepository.findAllEvents();
        events = filterDismissedEvents(events);
        notificationProducer.produce(events);
    }

    private List<CalendarEvent> filterDismissedEvents(List<CalendarEvent> events) {
        List<CalendarEvent> filteredEvents = new ArrayList<CalendarEvent>();
        for (CalendarEvent event : events) {
            EventNotification notification = new EventNotification(event);
            if (!notificationStatusRegister.isDismissed(notification.getNotificationId())) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}
