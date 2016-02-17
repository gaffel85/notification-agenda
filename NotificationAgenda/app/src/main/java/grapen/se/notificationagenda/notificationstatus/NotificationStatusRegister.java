package grapen.se.notificationagenda.notificationstatus;

/**
 * Created by ola on 17/02/16.
 */
public interface NotificationStatusRegister {

    boolean isDismissed(Integer notificationId);
    void didDismiss(Integer notificationId);
}
