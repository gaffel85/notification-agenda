package grapen.se.notificationagenda.model;

/**
 * Created by ola on 17/02/16.
 */
public interface NotificationStatusRegister {

    boolean isDismissed(Integer notificationId);
    void didDismiss(Integer notificationId);
}
