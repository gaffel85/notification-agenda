package grapen.se.notificationagenda.calendar.androidcalendar;

import android.content.ContentResolver;

import java.util.List;

import grapen.se.notificationagenda.calendar.Calendar;

/**
 * Created by ola on 14/02/16.
 */
public class AndroidCalendar implements Calendar {
    private long id;
    private boolean visible;
    private boolean selected;
    private String name;

    public AndroidCalendar(long id, boolean visible, String name) {
        this.id = id;
        this.visible = visible;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public String getName() {
        return name;
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
