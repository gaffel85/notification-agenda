package grapen.se.notificationagenda.appcontext;

import android.preference.PreferenceFragment;

/**
 * Created by ola on 19/02/16.
 */
public class AppContextPreferenceFragment extends PreferenceFragment {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }
}
