package grapen.se.notificationagenda;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import grapen.se.notificationagenda.calendar.CalendarEvent;
import grapen.se.notificationagenda.calendar.CalendarRepository;
import grapen.se.notificationagenda.notificationproducer.NotificationProducer;

/**
 * Created by ola on 17/02/16.
 */
public class NotificationAgendaController {

    private CalendarRepository calendarRepository;
    private NotificationProducer notificationProducer;

    public NotificationAgendaController(CalendarRepository calendarRepository, NotificationProducer notificationProducer) {
        this.calendarRepository = calendarRepository;
        this.notificationProducer = notificationProducer;
    }

    public void sendAgendaAsNotification() {
        ArrayList<CalendarEvent> events = calendarRepository.findAllEvents();
        notificationProducer.produce(events);
    }
}
