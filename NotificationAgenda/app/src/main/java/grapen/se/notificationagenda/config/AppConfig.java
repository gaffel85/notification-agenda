package grapen.se.notificationagenda.config;

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
