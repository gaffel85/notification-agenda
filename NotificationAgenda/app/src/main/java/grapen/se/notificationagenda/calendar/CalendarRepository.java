package grapen.se.notificationagenda.calendar;

import java.util.List;


/**
 * Created by ola on 16/02/16.
 */
public interface CalendarRepository {
    List<CalendarEvent> findAllEvents();

    List<Calendar> findVisibleCalendars();

    List<Calendar> findAllCalendars();
}
