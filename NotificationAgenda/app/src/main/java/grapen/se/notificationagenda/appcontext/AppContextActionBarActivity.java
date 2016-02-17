package grapen.se.notificationagenda.appcontext;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by ola on 17/02/16.
 */
public class AppContextActionBarActivity extends ActionBarActivity {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }

}
