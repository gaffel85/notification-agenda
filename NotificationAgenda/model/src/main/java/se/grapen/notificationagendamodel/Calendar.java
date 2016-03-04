package se.grapen.notificationagendamodel;

/**
 * Created by ola on 16/02/16.
 */
public interface Calendar {
    long getId();

    boolean isVisible();

    String getName();

    void setSelected();

    boolean isSelected();
}
