package se.grapen.notificationagendacalendar.dataaccess.implementation;

import se.grapen.notificationagendacalendar.dataaccess.CalendarDO;

/**
 * Created by ola on 18/03/16.
 */
public class AndroidCalendarDO implements CalendarDO {
    private long calendarID;
    private boolean visible;
    private String name;

    public AndroidCalendarDO(long calendarID, boolean visible, String name) {
        this.calendarID = calendarID;
        this.visible = visible;
        this.name = name;
    }

    @Override
    public long getCalendarID() {
        return calendarID;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public String getName() {
        return name;
    }
}
