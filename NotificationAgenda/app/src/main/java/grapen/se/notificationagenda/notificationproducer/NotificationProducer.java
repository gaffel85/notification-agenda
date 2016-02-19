package grapen.se.notificationagenda.notificationproducer;

import java.util.List;

import grapen.se.notificationagenda.calendar.CalendarEvent;

/**
 * Created by ola on 17/02/16.
 */
public interface NotificationProducer {

    void produce(List<CalendarEvent> events);
}
