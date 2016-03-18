package se.grapen.notificationagendacalendar.dataaccess;

import java.util.List;

/**
 * Created by ola on 11/03/16.
 */
public interface CalendarEventDAO {

    List<CalendarEventDO> findEvents(Long calendarId, long startTimestamp, long endTimestamp);
}
