package com.mja;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Period;

public class FeeSchedule {

    public static final int FIRST_HOUR = 10;
    public static final int FIRST_THREE_HOUR = 25;
    public static final int EACH_DAY = 100;
    public static final int WEEKEND_MULTIPLY = 2;

    public static int calculateFee(LocalDateTime startTime, LocalDateTime endTime) {

        int fee;
        int daysAmount;
        Period period;

        if (startTime.plusHours(1).isAfter(endTime)) {
            fee = FIRST_HOUR;
        } else if (startTime.plusHours(3).isAfter(endTime)) {
            fee = FIRST_THREE_HOUR;
        } else {
            period = Period.between(startTime.toLocalDate(), endTime.toLocalDate());
            daysAmount = period.getDays();
            fee = daysAmount * EACH_DAY;
        }

        for (int i = 0; (startTime.toLocalDate().plusDays(i)).equals(endTime.toLocalDate()) ; i++) {
            if (startTime.getDayOfWeek() == DayOfWeek.SATURDAY || startTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                if (i == 0){
                    fee = fee*WEEKEND_MULTIPLY;
                } else {
                    fee = fee + 100;
                }
            }
        }
        return fee;
    }
}
