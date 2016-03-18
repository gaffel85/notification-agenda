package se.grapen.notificationagendacalendar.implementation;


import se.grapen.notificationagendacalendar.Calendar;
import se.grapen.notificationagendacalendar.dataaccess.CalendarDO;

/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendar implements Calendar {

    private CalendarDO data;
    private boolean selected;

    public AndroidCalendar(CalendarDO calendarDO) {
        this.data = calendarDO;
    }

    @Override
    public long getId() {
        return data.getCalendarID();
    }

    @Override
    public boolean isVisible() {
        return data.isVisible();
    }

    @Override
    public String getName() {
        return data.getName();
    }

    @Override
    public void setSelected() {
        selected = true;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}
