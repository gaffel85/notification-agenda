package se.grapen.notificationagendacalendar;

import java.util.List;
import java.util.Set;


/**
 * Created by ola on 16/02/16.
 */
public interface CalendarRepository {

    List<CalendarEvent> findAllEvents(long startTimestamp, Set<Long> calendarIds);

    List<Calendar> findVisibleCalendars();

    List<Calendar> findAllCalendars(Set<Long> markedCalendarIds);
}
