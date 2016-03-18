package se.grapen.notificationagendacalendar.dataaccess.implementation;

import se.grapen.notificationagendacalendar.dataaccess.CalendarEventDO;

/**
 * Created by ola on 18/03/16.
 */
public class AndroidCalendarEventDO implements CalendarEventDO {

    public final long eventID;
    public final long calId;
    public final String eventTitle;
    public final boolean allDay;
    public final long startDT;
    public final long endDT;
    public final String rrule;
    public final String rdate;
    public final String exrule;
    public final String exdate;

    public AndroidCalendarEventDO(long eventID, long calId, String eventTitle, boolean allDay, long startDT, long endDT, String rrule, String rdate, String exrule, String exdate) {
        this.eventID = eventID;
        this.calId = calId;
        this.eventTitle = eventTitle;
        this.allDay = allDay;
        this.startDT = startDT;
        this.endDT = endDT;
        this.rrule = rrule;
        this.rdate = rdate;
        this.exrule = exrule;
        this.exdate = exdate;
    }

    @Override
    public long getCalendarId() {
        return calId;
    }

    @Override
    public String getEventTitle() {
        return eventTitle;
    }

    @Override
    public boolean isAllDay() {
        return allDay;
    }

    @Override
    public long getStartDT() {
        return startDT;
    }

    @Override
    public long getEndDT() {
        return endDT;
    }

    @Override
    public String getRrule() {
        return rrule;
    }

    @Override
    public String getRdate() {
        return rdate;
    }

    @Override
    public String getExrule() {
        return exrule;
    }

    @Override
    public String getExdate() {
        return exdate;
    }

    @Override
    public long getEventID() {
        return eventID;
    }
}
