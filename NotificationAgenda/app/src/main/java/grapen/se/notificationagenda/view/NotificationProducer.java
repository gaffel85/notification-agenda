package grapen.se.notificationagenda.view;

import java.util.List;

import grapen.se.notificationagenda.model.CalendarEvent;

/**
 * Created by ola on 17/02/16.
 */
public interface NotificationProducer {

    void produce(List<CalendarEvent> events);
}
