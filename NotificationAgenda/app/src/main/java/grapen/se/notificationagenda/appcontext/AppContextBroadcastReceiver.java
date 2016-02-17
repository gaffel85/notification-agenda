package grapen.se.notificationagenda.appcontext;

import android.content.BroadcastReceiver;

/**
 * Created by ola on 17/02/16.
 */
public abstract class AppContextBroadcastReceiver extends BroadcastReceiver {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }

}
