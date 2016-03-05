package se.grapen.notificationagendamodel.implementation;

import com.android.calendarcommon2.RecurrenceSet;

import java.util.Date;

import se.grapen.notificationagendamodel.CalendarEvent;


/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendarEvent implements CalendarEvent {

    private long eventID;
    private final String displayName;
    private boolean allDay;
    private long startTs;
    private long endTs;
    private long calendarID;

    public AndroidCalendarEvent(long eventID, long calendarId, String displayName, boolean allDay, long startTs, long endTs, String rrule, String rdate, String exrule, String exdate) {
        this.eventID = eventID;
        this.displayName = displayName;
        this.allDay = allDay;
        this.startTs = startTs;
        this.endTs = endTs;
        this.calendarID = calendarId;
        takeCareRecurrence(rrule, rdate, exrule, exdate);
    }

    private void takeCareRecurrence(String rrule, String rdate, String exrule, String exdate) {
        RecurrenceSet recurrenceSet = new RecurrenceSet(rrule, rdate, exrule, exdate);
        long[] dates = recurrenceSet.rdates;
        dates = dates == null ? new long[0] : dates;
        long duration = endTs - startTs;

        for (long recurrenceDate : dates) {
            if (recurrenceDate > startTs && recurrenceDate < endTs) {
                startTs = recurrenceDate;
                endTs = startTs + duration;
                return;
            }
        }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public long getId() {
        return this.eventID;
    }

    @Override
    public long getStartTimestamp() {
        return startTs;
    }

    public boolean isOnNextDay() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

        return startTs >= calendar.getTimeInMillis();
    }

    @Override
    public boolean isAllDay() {
        return allDay;
    }

    @Override
    public boolean isAllDayToday() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        long todayStartTs = calendar.getTimeInMillis();
        Date date1 = new Date(todayStartTs);

        calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        long todayEndTs = calendar.getTimeInMillis();
        Date date2 = new Date(todayEndTs);

        Date date3 = new Date(startTs);
        Date date4 = new Date(endTs);

        return startTs <= todayStartTs && endTs >= todayEndTs;
    }
}
