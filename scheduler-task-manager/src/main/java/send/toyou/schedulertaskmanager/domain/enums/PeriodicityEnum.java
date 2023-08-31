package send.toyou.schedulertaskmanager.domain.enums;

public enum PeriodicityEnum {
    DAILY;

    public static PeriodicityEnum fromCronExpression(String cronExpression) {
        switch (cronExpression) {
            case "*" -> {
                return DAILY;
            }
            default -> {
                return null;
            }
        }
    }
}
