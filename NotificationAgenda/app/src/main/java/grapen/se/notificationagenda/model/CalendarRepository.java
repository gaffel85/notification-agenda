package grapen.se.notificationagenda.model;

import java.util.List;


/**
 * Created by ola on 16/02/16.
 */
public interface CalendarRepository {
    List<CalendarEvent> findAllEvents();

    List<Calendar> findVisibleCalendars();

    List<Calendar> findAllCalendars();
}
