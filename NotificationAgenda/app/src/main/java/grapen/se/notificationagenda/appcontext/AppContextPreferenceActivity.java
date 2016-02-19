package grapen.se.notificationagenda.appcontext;

import android.preference.PreferenceActivity;

/**
 * Created by ola on 19/02/16.
 */
public class AppContextPreferenceActivity extends PreferenceActivity {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }
}
