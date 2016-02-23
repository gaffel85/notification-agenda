package grapen.se.notificationagenda.config;

import java.util.List;

/**
 * Created by ola on 19/02/16.
 */
public interface AppConfig {

    int runCalenderCheckAtHour();

    int runCalenderCheckAtMin();

    List<Long> calendarsToUseIDs();
}
