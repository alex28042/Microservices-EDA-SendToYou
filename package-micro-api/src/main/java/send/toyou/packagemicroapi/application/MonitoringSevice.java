package send.toyou.packagemicroapi.application;

public interface MonitoringSevice {
    void sendValidationErrors(Object object, String errorFrom, String description);
}
