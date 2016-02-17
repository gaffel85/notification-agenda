package grapen.se.notificationagenda.appcontext;

import android.app.Activity;

/**
 * Created by ola on 17/02/16.
 */
public abstract class AppContextActivity extends Activity {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }
}
