package grapen.se.notificationagenda.model;

import java.util.List;
import java.util.Set;

/**
 * Created by ola on 19/02/16.
 */
public interface AppConfig {

    int runCalenderCheckAtHour();

    int runCalenderCheckAtMin();

    Set<Long> calendarsToUseIDs();
}
