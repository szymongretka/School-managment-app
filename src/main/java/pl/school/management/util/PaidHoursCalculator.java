package pl.school.management.util;

import java.time.LocalDateTime;

public class PaidHoursCalculator {

    private static final int FREE_ENTRANCE_HOUR = 7;
    private static final int FREE_EXIT_HOUR = 11;

    public static Integer calculate(LocalDateTime from, LocalDateTime to) {
        int entranceHour = from.getHour();
        int exitHour = to.getHour();

        if (entranceHour >= FREE_ENTRANCE_HOUR && exitHour <= FREE_EXIT_HOUR) {
            return 0;
        }

        if (entranceHour < FREE_ENTRANCE_HOUR && exitHour > FREE_EXIT_HOUR) {
            int hours = 0;
            hours += FREE_ENTRANCE_HOUR - entranceHour;
            hours += exitHour - FREE_EXIT_HOUR;
            return hours;
        }

        if (entranceHour < FREE_ENTRANCE_HOUR && exitHour < FREE_ENTRANCE_HOUR) {
            return exitHour - entranceHour + 1;
        }

        if (entranceHour > FREE_EXIT_HOUR) {
            return exitHour - entranceHour + 1;
        }

        if (entranceHour < FREE_ENTRANCE_HOUR && exitHour <= FREE_EXIT_HOUR && exitHour > FREE_ENTRANCE_HOUR) {
            return FREE_ENTRANCE_HOUR - entranceHour;
        }

        if (entranceHour >= FREE_ENTRANCE_HOUR && entranceHour <= FREE_EXIT_HOUR && exitHour > FREE_EXIT_HOUR) {
            return exitHour - FREE_EXIT_HOUR;
        }

        return 0;
    }

}
