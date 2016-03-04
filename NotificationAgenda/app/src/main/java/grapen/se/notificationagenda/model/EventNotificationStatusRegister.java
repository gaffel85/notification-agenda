package grapen.se.notificationagenda.model;

/**
 * Created by ola on 17/02/16.
 */
public interface EventNotificationStatusRegister {

    boolean isDismissed(Long eventId);
    void didDismiss(Long eventId);
}
