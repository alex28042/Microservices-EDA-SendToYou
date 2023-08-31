package send.toyou.schedulertaskmanager.domain.enums;

import java.time.DayOfWeek;

public enum DaysCronEnum {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT,
    SUN;

    public static DaysCronEnum fromWeekDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY -> {
                return MON;
            }
            case FRIDAY -> {
                return FRI;
            }
            case SUNDAY -> {
                return SUN;
            }
            case TUESDAY -> {
                return TUE;
            }
            case SATURDAY -> {
                return SAT;
            }
            case THURSDAY -> {
                return THU;
            }
            case WEDNESDAY -> {
                return WED;
            }
            default -> {
                return null;
            }
        }
    }

    public static DayOfWeek toDayOfWeek(DaysCronEnum dayOfWeek) {
        switch (dayOfWeek) {
            case MON -> {
                return DayOfWeek.MONDAY;
            }
            case FRI -> {
                return DayOfWeek.FRIDAY;
            }
            case SUN -> {
                return DayOfWeek.SUNDAY;
            }
            case TUE -> {
                return DayOfWeek.TUESDAY;
            }
            case SAT -> {
                return DayOfWeek.SATURDAY;
            }
            case THU -> {
                return DayOfWeek.THURSDAY;
            }
            case WED -> {
                return DayOfWeek.WEDNESDAY;
            }
            default -> {
                return null;
            }
        }
    }
}
