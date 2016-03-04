package grapen.se.notificationagenda.model.implementation;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import grapen.se.notificationagenda.model.EventNotificationStatusRegister;

/**
 * Created by ola on 17/02/16.
 */
public class SharedPreferenceNotificationStatusRegister implements EventNotificationStatusRegister {
    private static final String PREF_FILE_NAME = "DISMISSED_EVENTS_V1";
    private static final String EVENT_PREF_NAME_BIG = "EVENT_PREF_NAME_BIG";
    private static final String EVENT_PREF_NAME_BIG_CNT = "EVENT_PREF_NAME_BIG_CNT";

    private Context androidContext;

    public SharedPreferenceNotificationStatusRegister(Context androidContext) {
        this.androidContext = androidContext;
    }

    @Override
    public boolean isDismissed(Long eventId) {
        return false;
        //return getDismissedSet().contains(notificationId.toString());
    }

    private Set<String> getDismissedSet() {
        SharedPreferences settings = androidContext.getSharedPreferences(PREF_FILE_NAME, 0);
        Set<String> dismissedIds = settings.getStringSet(EVENT_PREF_NAME_BIG, new HashSet<String>());
        return dismissedIds;
    }

    @Override
    public void didDismiss(Long eventId) {
        SharedPreferences settings = androidContext.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Set<String> dismissedIds = settings.getStringSet(EVENT_PREF_NAME_BIG, new HashSet<String>());
        dismissedIds.add(eventId.toString());
        editor.putStringSet(EVENT_PREF_NAME_BIG, dismissedIds);
        editor.putInt(EVENT_PREF_NAME_BIG_CNT, dismissedIds.size());

        editor.commit();
    }
}
